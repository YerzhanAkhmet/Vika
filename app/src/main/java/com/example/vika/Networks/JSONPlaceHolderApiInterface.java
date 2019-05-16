package com.example.vika.Networks;

import com.example.vika.Classes.Post;
import com.example.vika.Classes.Users;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface JSONPlaceHolderApiInterface {
    @GET("posts/{id}")
    Observable<Post> getPostByID(@Path("id") int id);

    @GET("posts")
    Observable<List<Post>> getAllPosts();

    @DELETE("posts/{id}")
    Observable<Post> deletePostByID(@Path("id") int id);

    @GET("users/{id}")
    Observable<Users> getUserByID(@Path ("id") int id);

    @GET("users/")
    Observable<List<Users>> getAllUsers();
}
