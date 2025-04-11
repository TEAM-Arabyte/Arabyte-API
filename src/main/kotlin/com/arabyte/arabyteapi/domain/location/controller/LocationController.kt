package com.arabyte.arabyteapi.domain.location.controller

import com.arabyte.arabyteapi.domain.location.entity.Location
import com.arabyte.arabyteapi.domain.location.service.LocationService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/locations")
class LocationController(
    private val locationService: LocationService
) {
    @Operation(summary = "시도 조회", description = "시도 목록을 조회합니다.")
    @GetMapping("/sido")
    fun getSidoList(): List<Location> {
        return locationService.getSidoList()
    }

    @Operation(summary = "시군구 조회", description = "시군구 목록을 조회합니다.")
    @GetMapping("/gu")
    fun getGuList(
        @RequestParam sidoCode: String
    ): List<Location> {
        return locationService.getGuList(sidoCode)
    }

    @Operation(summary = "읍면동 조회", description = "읍면동 목록을 조회합니다.")
    @GetMapping("/dong")
    fun getDongList(
        @RequestParam sidoCode: String,
        @RequestParam guCode: String
    ): List<Location> {
        return locationService.getDongList(sidoCode, guCode)
    }
}