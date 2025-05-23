package com.example.fake_server.db

import androidx.room.Embedded
import androidx.room.Relation

data class PostWithAuthor(
    @Embedded val post: PostEntity,
    @Relation(
        parentColumn = "authorId",
        entityColumn = "id"
    )
    val author: UserEntity
)