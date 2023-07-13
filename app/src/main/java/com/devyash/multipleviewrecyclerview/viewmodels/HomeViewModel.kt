import androidx.lifecycle.*
import com.devyash.multipleviewrecyclerview.models.HomeRecylerViewItem
import com.devyash.multipleviewrecyclerview.network.NetworkResult
import com.devyash.multipleviewrecyclerview.repositories.HomeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private lateinit var homeRepository: HomeRepository

    private val _homeListItems = MutableLiveData<NetworkResult<List<HomeRecylerViewItem>>>()
    val homeListItems: LiveData<NetworkResult<List<HomeRecylerViewItem>>> get() = _homeListItems

    init {
        homeRepository = HomeRepository()
        getHomeListItems()
    }

    private fun getHomeListItems() = viewModelScope.launch {
        _homeListItems.value = NetworkResult.Loading()

        try {
            val moviesDeferred = async { homeRepository.getMovies() }
            val directorsDeferred = async { homeRepository.getDirectors() }

            val movies = moviesDeferred.await()
            val directors = directorsDeferred.await()

            val homeItemsList = mutableListOf<HomeRecylerViewItem>()
            if (movies.isSuccessful && directors.isSuccessful) {
                homeItemsList.add(HomeRecylerViewItem.Title(1, "Recommended Movies"))
                homeItemsList.addAll(movies.body()!!)
                homeItemsList.add(HomeRecylerViewItem.Title(2, "Top Directors"))
                homeItemsList.addAll(directors.body()!!)

                _homeListItems.value = NetworkResult.Success(homeItemsList)
            } else {
                _homeListItems.value = NetworkResult.Error("", null)
            }

        } catch (e: Exception) {
            _homeListItems.value = NetworkResult.Error(e.message)
        }
    }
}
