package com.arabyte.arabyteapi.domain.location.service

import com.arabyte.arabyteapi.domain.location.entity.Location
import com.arabyte.arabyteapi.domain.location.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationRepository: LocationRepository
) {
    fun getSidoList(): List<Location> {
        return locationRepository.findAllByDepth(1)
    }

    fun getGuList(sidoCode: String): List<Location> {
        return locationRepository.findAllBySidoCodeAndDepth(sidoCode, 2)
    }

    fun getDongList(sidoCode: String, guCode: String): List<Location> {
        return locationRepository.findAllBySidoCodeAndGuCodeAndDepth(sidoCode, guCode, 3)
    }
}