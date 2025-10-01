package com.example.post

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Получаем ViewModel с помощью делегата. Android сам позаботится о его жизненном цикле.
    private val postViewModel: PostViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        // Инициализируем адаптер с пустым списком и передаем лямбду для обработки клика на лайк.
        postAdapter = PostAdapter(emptyList()) { post ->
            // При клике вызываем метод из ViewModel.
            postViewModel.onLikeClicked(post.id)
        }

        // Настраиваем RecyclerView.
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = postAdapter

        // Подписываемся на изменения в списке постов из ViewModel.
        postViewModel.posts.observe(this) { posts ->
            // Как только данные изменяются (например, после лайка), этот код выполняется.
            posts?.let {
                postAdapter.updatePosts(it)
            }
        }
    }
}