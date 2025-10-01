package com.example.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class PostAdapter(
    private var posts: List<Post>,
    private val onLikeClicked: (Post) -> Unit // Лямбда-функция для обработки клика
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    // Создает и "надувает" (inflate) макет для одного элемента и возвращает ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    // Привязывает данные к view-элементам в конкретной позиции.
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    // Возвращает общее количество постов.
    override fun getItemCount(): Int = posts.size

    // ViewHolder хранит ссылки на view-элементы каждого поста для быстрого доступа.
    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postImageView: ImageView = itemView.findViewById(R.id.postImageView)
        private val postTextView: TextView = itemView.findViewById(R.id.postTextView)
        private val likeButton: ImageButton = itemView.findViewById(R.id.likeButton)

        fun bind(post: Post) {
            postTextView.text = post.text
            // Используем Coil для загрузки картинки по URL[cite: 7, 18].
            postImageView.load(post.imageUrl) {
                crossfade(true) // Плавное появление
                placeholder(R.drawable.ic_launcher_background) // Картинка-заглушка
            }

            // Устанавливаем иконку лайка в зависимости от статуса[cite: 25].
            if (post.isLiked) {
                likeButton.setImageResource(R.drawable.ic_thumb_up_alt)
            } else {
                likeButton.setImageResource(R.drawable.ic_thumb_up_off_alt)
            }

            // Устанавливаем обработчик клика на кнопку лайка.
            likeButton.setOnClickListener {
                onLikeClicked(post)
            }
        }
    }

    // Функция для обновления списка постов в адаптере.
    fun updatePosts(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged() // Уведомляем адаптер, что данные изменились.
    }
}