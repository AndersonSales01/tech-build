package com.tech.building.gateway

import com.tech.building.gateway.collaborator.entity.CollaboratorDTO
import com.tech.building.gateway.login.entity.UserDTO
import com.tech.building.gateway.material.entity.MaterialDTO

object MocksData {

    fun mockListCollaborators(): List<CollaboratorDTO> =
        listOf(
            CollaboratorDTO("Anderson Carlos", 12, "11111111111","20000010"),
            CollaboratorDTO("Antonio Carlos", 42, "222222","20000011"),
            CollaboratorDTO("Halyson Ribeiro Pessoa", 15, "333333","20000012")
        )

    fun mockListLogin(): List<UserDTO> =
        listOf(
            UserDTO(
                user = "teste1",
                password = "123456",
                name = "João Paulo",
                email = "teste@gmail.com",
                phone = "8888-88888",
                userProfile = "ADMIN",
                permissions = listOf("new_request", "release_request")
            ),
            UserDTO(
                user = "teste2",
                password = "123456",
                name = "teste2",
                email = "teste1@gmail.com",
                phone = "9999-9999",
                userProfile = "ENGINEER",
                permissions = listOf("new_request")
            ),
            UserDTO(
                user = "teste3",
                password = "123456",
                name = "teste2",
                email = "teste1@gmail.com",
                phone = "9999-9999",
                userProfile = "WAREHOUSE",
                permissions = listOf("release_request")
            ),
        )

    fun mockListMaterials(): List<MaterialDTO> =
        listOf(
            MaterialDTO("Cimento CP", "1111", listOf("UNiD")),
            MaterialDTO("Manta impermeabilizante ", "2222", listOf("RL", "M")),
            MaterialDTO("Prego", "333", listOf("KG", "UUNID")),
        )
}