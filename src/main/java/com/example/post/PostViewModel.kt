package com.example.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {

    // _posts - это изменяемые данные (MutableLiveData), видимые только внутри ViewModel.
    private val _posts = MutableLiveData<List<Post>>()
    // posts - это публичная, неизменяемая версия (LiveData), на которую будет подписываться UI.
    val posts: LiveData<List<Post>> = _posts

    init {
        // Проверяем, были ли данные уже загружены, чтобы не сбрасывать их при повороте.
        if (_posts.value == null) {
            loadPosts()
        }
    }

    private fun loadPosts() {
        // Создаем "моковые" (тестовые) данные, как требуется в задании[cite: 5].
        val postList = mutableListOf<Post>()
        postList.add(Post(1, "Красивый закат на пляже!", "https://picsum.photos/seed/picsum/400/300"))
        postList.add(Post(2, "Исследуем горы.", "https://picsum.photos/seed/abc/400/300"))
        postList.add(Post(3, "Вкусный завтрак.", "https://picsum.photos/seed/xyz/400/300"))
        postList.add(Post(4, "Огни ночного города.", "https://picsum.photos/seed/hello/400/300"))
        _posts.value = postList
    }

    // Функция для обработки нажатия на лайк[cite: 10].
    fun onLikeClicked(postId: Int) {
        val currentPosts = _posts.value?.toMutableList() ?: return
        val postIndex = currentPosts.indexOfFirst { it.id == postId }
        if (postIndex != -1) {
            val post = currentPosts[postIndex]
            post.isLiked = !post.isLiked // Инвертируем статус лайка
            _posts.value = currentPosts // Обновляем LiveData, что вызовет перерисовку UI.
        }
    }
}