package com.desiatko.countries.data.service

import com.desiatko.countries.domain.repository.entity.Field

class Filter(private val fields: Array<out Field>) {

    override fun toString(): String = fields.joinToString(separator = ";", transform = Field::fullName)
}