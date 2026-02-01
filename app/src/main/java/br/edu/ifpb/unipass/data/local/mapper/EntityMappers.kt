package br.edu.ifpb.unipass.data.local.mapper

import br.edu.ifpb.unipass.data.local.entity.StudentCardEntity
import br.edu.ifpb.unipass.data.local.entity.TripEntity
import br.edu.ifpb.unipass.data.local.entity.UserEntity
import br.edu.ifpb.unipass.models.StudentCard
import br.edu.ifpb.unipass.models.Trip
import br.edu.ifpb.unipass.models.TripStatus
import br.edu.ifpb.unipass.models.User

// Trip Mappers

// Converte TripEntity (Room) para Trip (Model)
fun TripEntity.toTrip(): Trip {
    return Trip(
        id = this.id,
        date = this.date,
        time = this.time,
        origin = this.origin,
        destination = this.destination,
        seatNumber = this.seatNumber,
        status = TripStatus.fromValue(this.status),
        reservedSeats = this.reservedSeats,
        totalSeats = this.totalSeats
    )
}

// Converte Trip (Model) para TripEntity (Room)
fun Trip.toEntity(userId: String): TripEntity {
    return TripEntity(
        id = this.id,
        date = this.date,
        time = this.time,
        origin = this.origin,
        destination = this.destination,
        seatNumber = this.seatNumber,
        status = this.status.value,
        reservedSeats = this.reservedSeats,
        totalSeats = this.totalSeats,
        userId = userId
    )
}

// User Mappers
fun UserEntity.toUser(): User {
    val initials = this.name.split(" ")
        .take(2)
        .mapNotNull { it.firstOrNull()?.uppercase() }
        .joinToString("")

    return User(
        name = this.name,
        initials = initials,
        notificationCount = this.notificationCount
    )
}

fun User.toEntity(id: String, cpf: String, email: String = ""): UserEntity {
    return UserEntity(
        id = id,
        name = this.name,
        cpf = cpf,
        email = email,
        notificationCount = this.notificationCount
    )
}

// StudentCard Mappers
fun StudentCardEntity.toStudentCard(): StudentCard {
    return StudentCard(
        id = this.id,
        studentName = this.studentName,
        institution = this.institution,
        course = this.course,
        shift = this.shift,
        cardNumber = this.cardNumber,
        isActive = this.isActive,
        validUntil = this.validUntil,
        photoUrl = this.photoUrl
    )
}

fun StudentCard.toEntity(userId: String): StudentCardEntity {
    return StudentCardEntity(
        id = this.id,
        userId = userId,
        studentName = this.studentName,
        institution = this.institution,
        course = this.course,
        shift = this.shift,
        cardNumber = this.cardNumber,
        isActive = this.isActive,
        validUntil = this.validUntil,
        photoUrl = this.photoUrl
    )
}