package com.kamalbramwell.dating.user.model

import androidx.annotation.StringRes
import com.kamalbramwell.dating.R
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

data class UserData(
    val uid: String = "",
    val name: String = "",
    val birthday: Long = 0,
    val gender: GenderOption = GenderOption.Error,
    val seeking: Seeking = Seeking.Error,
    val photoUrl: String = "",
    val matchQuestion: String = ""
) {

    companion object {
        private val maleNames = listOf(
            "Miles Delacruz",
            "Rafael Taylor",
            "Madison Cook",
            "Lyndon Kemp",
            "Mohammad Friedman",
            "Brett Horton",
            "Homer Zimmerman",
            "Fahad Daniels",
            "Joel Riley",
            "Yusuf Combs"
        )
        private val femaleNames = listOf(
            "Fatima Weeks",
            "Stevie Kemp",
            "Taha Mccarty",
            "Scarlet Berg",
            "Polly Mahoney",
            "Nora Vincent",
            "Maisha Mcintyre",
            "Enya Bird",
            "Linda Marquez",
            "Kristen Mitchell",
        )
        private val RandomBetween1985and2000
            get() = Random.nextLong(
                LocalDate.of(1985, 1,1).toEpochDay(),
                LocalDate.of(2000, 1, 1).toEpochDay()
            )
        private val malePhotos = listOf(
            "https://images.unsplash.com/photo-1618641986557-1ecd230959aa?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        )
        private val femalePhotos = listOf(
            "https://images.unsplash.com/photo-1668069574922-bca50880fd70?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
            "https://images.unsplash.com/photo-1667935764607-73fca1a86555?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=688&q=80"
        )
        private val matchQuestions = listOf(
            "What\'s your idea of the perfect relationship?",
            "What\'s a date idea you\'ve never tried?",
            "Are you a robot?",
            "Are you a Patriots fan?",
            "Tell me a word that rhymes with purple",
            "Have you ever been on a game show?",
            "If you could have any super power, what would you choose?",
            "Are you into motorcycles?",
            "What\'s your favorite Rick and Morty episode?",
            "What song can\'t you keep out your head lately?"
        )
        fun random(): UserData {
            val male = Random.nextBoolean()
            return UserData(
                uid = UUID.randomUUID().toString(),
                name = if (male) maleNames.random() else femaleNames.random(),
                birthday = RandomBetween1985and2000,
                gender = if (male) GenderOption.Male else GenderOption.Female,
                seeking = Seeking.values().filter { it.id >= 0}.random(),
                photoUrl = if (male) malePhotos.random() else femalePhotos.random(),
                matchQuestion = matchQuestions.random()
            )
        }
        fun generateUsers(male: Boolean): List<UserData> {
            val namesList = if (male) maleNames else femaleNames
            return namesList.mapIndexed { idx, name ->
                UserData(
                    uid = UUID.randomUUID().toString(),
                    name = name,
                    birthday = RandomBetween1985and2000,
                    gender = GenderOption.Male,
                    seeking = Seeking.values().random(),
                    photoUrl = malePhotos[idx % malePhotos.size],
                    matchQuestion = matchQuestions[idx % matchQuestions.size]
                )
            }
        }
    }
}

enum class GenderOption(@StringRes val label: Int, val id: Int) {
    Male(R.string.gender_male, 0),
    Female(R.string.gender_female, 1),
    Nonbinary(R.string.gender_nonbinary, 2),
    Error(R.string.error_missing_property, -1)
}

enum class Seeking(@StringRes val label: Int, val id: Int) {
    Friends(R.string.seeking_friends, 0),
    FWB(R.string.seeking_fwb, 1),
    Casual(R.string.seeking_casual, 2),
    Dating(R.string.seeking_dating, 3),
    Relationship(R.string.seeking_ltr, 4),
    Marriage(R.string.seeking_marriage, 5),
    Error(R.string.error_missing_property, -1)
}