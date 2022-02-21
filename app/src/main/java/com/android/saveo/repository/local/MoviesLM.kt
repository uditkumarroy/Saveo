package com.android.saveo.repository.local

import com.android.saveo.models.MoviesModel
import com.android.saveo.ui.movieslist.MoviesItemView
import com.android.saveo.utils.EntityMapping
import javax.inject.Inject

class MoviesLM @Inject constructor() : EntityMapping<MoviesLocalEntityItem, MoviesModel> {


    fun mapFromEntityToModel(entities: List<MoviesLocalEntityItem>): List<MoviesModel> {
        return entities.map { mapFromEntity(it) }
    }

    fun mapFromEntityToVewTypeModel(entities: List<MoviesModel>): List<MoviesItemView> {
        return entities.map { MoviesItemView(it)}
    }

    fun mapFromModelToEntity(entities: List<MoviesModel>): List<MoviesLocalEntityItem> {
        return entities.map { mapToEntity(it) }
    }

    override fun mapFromEntity(entity: MoviesLocalEntityItem): MoviesModel {
        return MoviesModel(
            entity.author,
            entity.download_url,
            entity.height,
            entity.id,
            entity.url,
            entity.width
        )
    }

    override fun mapToEntity(domainModel: MoviesModel): MoviesLocalEntityItem {
        return MoviesLocalEntityItem(
            domainModel.author,
            domainModel.download_url,
            domainModel.height,
            domainModel.id,
            domainModel.url,
            domainModel.width
        )
    }
}