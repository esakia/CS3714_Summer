package com.example.pp04

data class Movies(val results: List<MovieItem>,
                  val total_pages: Int,
                  val page: Int)