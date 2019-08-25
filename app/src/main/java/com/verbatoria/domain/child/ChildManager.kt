package com.verbatoria.domain.child

import com.verbatoria.infrastructure.extensions.*
import com.verbatoria.infrastructure.retrofit.endpoints.child.ChildEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.child.model.params.ChildParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.child.model.params.CreateOrEditChildParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.child.model.response.ChildResponseDto
import java.lang.IllegalStateException

/**
 * @author n.remnev
 */

private const val MALE_GENDER_POSITION = 0
private const val FEMALE_GENDER_POSITION = 1

private const val MALE_GENDER = "male"
private const val FEMALE_GENDER = "female"

interface ChildManager {

    fun getChild(clientId: String, childId: String): Child

    fun createNewChild(clientId: String, child: Child): String

    fun editChild(clientId: String, child: Child)

    fun parseChildDto(childDto: ChildResponseDto): Child

}

class ChildManagerImpl(
    private val childEndpoint: ChildEndpoint
) : ChildManager {

    override fun getChild(clientId: String, childId: String): Child {
        val childResponse = childEndpoint.getChild(clientId, childId)
        return Child(
            id = childId,
            name = childResponse.name,
            gender = if (childResponse.gender == MALE_GENDER)
                MALE_GENDER_POSITION
            else
                FEMALE_GENDER_POSITION,
            age = childResponse.birthday.parseBirthdayFormat().getYearsForCurrentMoment()
        )
    }

    override fun createNewChild(clientId: String, child: Child): String {
        val createChildResponse = childEndpoint.createNewChild(
            clientId = clientId,
            params = CreateOrEditChildParamsDto(
                child = ChildParamsDto(
                    clientId = clientId,
                    name = child.name,
                    birthday = child.age.birthdayDateFromAge().formatWithMillisecondsAndZeroOffset(),
                    gender = if (child.gender == 0)
                        MALE_GENDER
                    else
                        FEMALE_GENDER
                )
            )
        )
        return createChildResponse.id
    }


    override fun editChild(clientId: String, child: Child) {
        childEndpoint.editChild(
            clientId = clientId,
            childId = child.id ?: throw IllegalStateException("try to edit child without id"),
            params = CreateOrEditChildParamsDto(
                child = ChildParamsDto(
                    clientId = clientId,
                    name = child.name,
                    birthday = child.age.birthdayDateFromAge().formatWithMillisecondsAndZeroOffset(),
                    gender = if (child.gender == 0)
                        MALE_GENDER
                    else
                        FEMALE_GENDER
                )
            )
        )
    }

    override fun parseChildDto(childDto: ChildResponseDto): Child =
        with(childDto) {
            Child(
                id = id,
                name = name,
                gender = if (gender == MALE_GENDER)
                    MALE_GENDER_POSITION
                else
                    FEMALE_GENDER_POSITION,
                age = birthday.parseBirthdayFormat().getYearsForCurrentMoment()
            )
        }

}