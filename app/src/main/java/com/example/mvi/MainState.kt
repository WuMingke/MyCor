//package com.example.mvi
//
//data class MainState(val bannerUiState: BannerUiState) : IUiState
//
//sealed class BannerUiState {
//    object INIT : BannerUiState()
//    data class SUCCESS(val models: List<BannerModel>) : BannerUiState()
//}
//
//sealed class DetailUiState {
//    object INIT : DetailUiState()
//    data class SUCCESS(val articles: ArticleModel) : DetailUiState()
//}