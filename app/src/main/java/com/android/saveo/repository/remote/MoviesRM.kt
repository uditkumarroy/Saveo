package com.android.saveo.repository.remote

import com.android.saveo.models.MoviesModel
import com.android.saveo.ui.movieslist.MoviesItemView
import com.android.saveo.utils.EntityMapping
import javax.inject.Inject

class MoviesRM @Inject constructor() : EntityMapping<MoviesRemoteEntityItem, MoviesModel> {


    fun mapFromEntityToModel(entities: List<MoviesRemoteEntityItem>): List<MoviesModel> {
        return entities.map { mapFromEntity(it) }
    }

    fun mapFromEntityToVewTypeModel(entities: List<MoviesModel>): List<MoviesItemView> {
        return entities.map { MoviesItemView(it)}
    }

    fun mapFromModelToEntity(entities: List<MoviesModel>): List<MoviesRemoteEntityItem> {
        return entities.map { mapToEntity(it) }
    }

    override fun mapFromEntity(entity: MoviesRemoteEntityItem): MoviesModel {
        return MoviesModel(
            entity.author,
            entity.download_url,
            entity.height,
            entity.id,
            entity.url,
            entity.width
        )
    }

    override fun mapToEntity(domainModel: MoviesModel): MoviesRemoteEntityItem {
        return MoviesRemoteEntityItem(
            domainModel.author,
            domainModel.download_url,
            domainModel.height,
            domainModel.id,
            domainModel.url,
            domainModel.width
        )
    }
}