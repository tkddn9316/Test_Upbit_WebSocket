package com.app.domain.usecase

import com.app.domain.model.Ticker
import com.app.domain.repository.TickerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTickerUseCase @Inject constructor(private val repository: TickerRepository) {

    operator fun invoke(): Flow<List<Ticker>> = repository.getTicker()
}