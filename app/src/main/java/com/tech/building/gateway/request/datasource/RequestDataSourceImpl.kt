package com.tech.building.gateway.request.datasource

import android.content.SharedPreferences
import android.util.Log
import com.tech.building.domain.model.RequestModel
import com.tech.building.gateway.request.entity.RequestDTO
import com.tech.building.gateway.util.fromJson
import com.tech.building.gateway.util.toJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

 const val REQUEST_DATA = "request_data"

class RequestDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
) : RequestDataSource {
    private var requests = mutableListOf<RequestDTO>()

    override fun saveNewRequest(requestModel: RequestModel): Flow<Unit> {
        requests.clear()
        getDataRequestPersisted()
        val requestDTO = RequestDTO(
            collaborator = requestModel.collaborator,
            itemsRequest = requestModel.itemsRequest,
            status = requestModel.status.name
        )
        requests.add(requestDTO)
        Log.d("saveNewRequest", "value" + requests.size)
        persistNewRequest()
        return flow {
            emit(Unit)
        }
    }

    private fun persistNewRequest() {
        clear()
        sharedPreferences.edit().putString(REQUEST_DATA, requests.toJson()).apply()
    }

    private fun getDataRequestPersisted() {
        val requestList = sharedPreferences.getString(REQUEST_DATA, null)
        requestList?.let {
            requests.addAll(it.toRequestSession())
        }
    }

    private fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    private fun String.toRequestSession(): List<RequestDTO> {
        return fromJson()
    }
}