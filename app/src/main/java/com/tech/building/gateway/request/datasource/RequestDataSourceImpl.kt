package com.tech.building.gateway.request.datasource

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.tech.building.domain.model.FilterRequestStatus
import com.tech.building.domain.model.RequestModel
import com.tech.building.domain.model.RequestStatus
import com.tech.building.gateway.request.entity.RequestDTO
import com.tech.building.gateway.request.mapper.ListRequestDtoToListRequestModelMapper
import com.tech.building.gateway.request.mapper.RequestModelToRequestDtoMapper
import com.tech.building.gateway.util.convertJson
import com.tech.building.gateway.util.fromJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val REQUEST_DATA = "request_data"

class RequestDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val listRequestDtoToListRequestMapper: ListRequestDtoToListRequestModelMapper,
    private val requestModelToRequestDtoMapper: RequestModelToRequestDtoMapper,
    private val gson: Gson
) : RequestDataSource {
    private var requests = mutableListOf<RequestDTO>()

    private fun saveNewRequest(requestModel: RequestModel) {
        requests.clear()
        getDataRequestPersisted()
        val requestDTO = RequestDTO(
            id = requests.size.inc(),
            collaborator = requestModel.collaborator,
            itemsRequest = requestModel.itemsRequest,
            status = requestModel.status.name
        )
        requests.add(requestDTO)
        persistNewRequest()
    }

    override fun sendRequest(requestModel: RequestModel): Flow<Unit> {
        return flow {
            saveNewRequest(requestModel)
            emit(Unit)
        }
    }

    override fun getRequestsFiltered(
        registrationCollaborator: String,
        filter: FilterRequestStatus
    ): Flow<List<RequestModel>> {
        getDataRequestPersisted()
        return flow {
            if (requests.isNotEmpty()) {
                emit(
                    listRequestDtoToListRequestMapper.map(
                        applyFilters(
                            registrationCollaborator = registrationCollaborator,
                            status = filter
                        )
                    )
                )
            }
        }
    }

    private fun persistNewRequest() {
        clear()
        sharedPreferences.edit().putString(REQUEST_DATA, toUserSession(requests)).apply()
    }

    private fun getDataRequestPersisted() {
        requests.clear()
        val requestList = sharedPreferences.getString(REQUEST_DATA, null)
        requestList?.let {
            requests.addAll(
                fromUserSession(it)
            )
        }
    }

    private fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    private fun String.toRequestSession(): List<RequestDTO> {
        return fromJson()
    }

    private fun applyFilters(
        registrationCollaborator: String,
        status: FilterRequestStatus
    ): List<RequestDTO> {
        if (registrationCollaborator.isNotEmpty() && status.name != FilterRequestStatus.ALL.name) {
            return requests.filter {
                it.collaborator.registration == registrationCollaborator && it.status == requestStatus(
                    status
                )
            }
        } else if (registrationCollaborator.isNotEmpty() && status.name == FilterRequestStatus.ALL.name) {
            return requests.filter {
                it.collaborator.registration == registrationCollaborator
            }
        } else if (registrationCollaborator.isNullOrEmpty() && status.name != FilterRequestStatus.ALL.name) {
            return requests.filter {
                it.status == requestStatus(
                    status
                )
            }
        }
        return requests
    }

    private fun requestStatus(status: FilterRequestStatus): String {
        if (status.name == FilterRequestStatus.PENDING.name) {
            return RequestStatus.PENDING.name
        }
        return RequestStatus.RELEASED.name
    }

    override fun releaseRequest(requestModel: RequestModel): Flow<Unit> {
        return flow {
            requestModel.status = RequestStatus.RELEASED
            updateRequest(requestDTO = requestModelToRequestDtoMapper.map(requestModel))
            emit(Unit)
        }
    }

    private fun updateRequest(requestDTO: RequestDTO) {
        getDataRequestPersisted()
        requests?.let {
            it.forEach { dto ->
                if (dto.id == requestDTO.id) {
                    requests[requests.indexOf(dto)] = requestDTO
                    persistNewRequest()
                }
            }
        }


    }


    private fun fromUserSession(json: String): List<RequestDTO> {
        return gson.convertJson<List<RequestDTO>>(json)
    }

    private fun toUserSession(requestsDTO: List<RequestDTO>): String {
        return gson.toJson(requestsDTO)
    }
}