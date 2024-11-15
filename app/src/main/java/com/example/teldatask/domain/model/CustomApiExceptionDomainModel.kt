package com.example.teldatask.domain.model

sealed class CustomApiExceptionDomainModel : Exception() {
    data object NoInternetConnectionExceptionDomainModel : CustomApiExceptionDomainModel() {
        private fun readResolve(): Any = NoInternetConnectionExceptionDomainModel
    }

    data object TimeoutExceptionDomainModel : CustomApiExceptionDomainModel() {
        private fun readResolve(): Any = TimeoutExceptionDomainModel
    }

    data object NetworkExceptionDomainModel : CustomApiExceptionDomainModel() {
        private fun readResolve(): Any = NetworkExceptionDomainModel
    }

    data object ServiceNotFoundExceptionDomainModel : CustomApiExceptionDomainModel() {
        private fun readResolve(): Any = ServiceNotFoundExceptionDomainModel
    }

    data object AccessDeniedExceptionDomainModel : CustomApiExceptionDomainModel() {
        private fun readResolve(): Any = AccessDeniedExceptionDomainModel
    }

    data object ServiceUnavailableExceptionDomainModel : CustomApiExceptionDomainModel() {
        private fun readResolve(): Any = ServiceUnavailableExceptionDomainModel
    }

    data object UnknownExceptionDomainModel : CustomApiExceptionDomainModel() {
        private fun readResolve(): Any = UnknownExceptionDomainModel
    }

}