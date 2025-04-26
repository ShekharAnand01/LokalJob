package com.shekhar.lokal.navigation

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shekhar.lokal.data.db.JobDetailEntity
import com.shekhar.lokal.data.model.ContactPreference
import com.shekhar.lokal.data.model.ContentV3
import com.shekhar.lokal.data.model.Creative
import com.shekhar.lokal.data.model.FeeDetails
import com.shekhar.lokal.data.model.JobDetail
import com.shekhar.lokal.data.model.JobTag
import com.shekhar.lokal.data.model.Location
import com.shekhar.lokal.data.model.PrimaryDetails
import java.lang.reflect.Type
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class BottomNavItemState(
    val title: String,
    val selectedIcon: Int,
    val route: Any
)

fun formatDate(input: String): String {
    val zonedDateTime = ZonedDateTime.parse(input)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return zonedDateTime.format(formatter)
}

fun toEntity(jobDetail: JobDetail): JobDetailEntity {
    return JobDetailEntity(
        id = jobDetail.id,
        advertiser = jobDetail.advertiser,
        amount = jobDetail.amount,
        button_text = jobDetail.button_text,
        city_location = jobDetail.city_location,
        company_name = jobDetail.company_name,
        contact_preference = jobDetail.contact_preference,
        contentV3 = jobDetail.contentV3,
        created_on = jobDetail.created_on,
        creatives = jobDetail.creatives,
        custom_link = jobDetail.custom_link,
        enable_lead_collection = jobDetail.enable_lead_collection,
        experience = jobDetail.experience,
        expire_on = jobDetail.expire_on,
        fb_shares = jobDetail.fb_shares,
        fee_details = jobDetail.fee_details,
        fees_charged = jobDetail.fees_charged,
        fees_text = jobDetail.fees_text,
        is_applied = jobDetail.is_applied,
        is_bookmarked = jobDetail.is_bookmarked,
        is_job_seeker_profile_mandatory = jobDetail.is_job_seeker_profile_mandatory,
        is_owner = jobDetail.is_owner,
        is_premium = jobDetail.is_premium,
        job_category = jobDetail.job_category,
        job_category_id = jobDetail.job_category_id,
        job_hours = jobDetail.job_hours,
        job_location_slug = jobDetail.job_location_slug,
        job_role = jobDetail.job_role,
        job_role_id = jobDetail.job_role_id,
        job_tags = jobDetail.job_tags,
        job_type = jobDetail.job_type,
        locality = jobDetail.locality,
        locations = jobDetail.locations,
        num_applications = jobDetail.num_applications,
        openings_count = jobDetail.openings_count,
        other_details = jobDetail.other_details,
        premium_till = jobDetail.premium_till,
        primary_details = jobDetail.primary_details,
        qualification = jobDetail.qualification,
        question_bank_id = jobDetail.question_bank_id,
        salary_max = jobDetail.salary_max,
        salary_min = jobDetail.salary_min,
        screening_retry = jobDetail.screening_retry,
        shares = jobDetail.shares,
        shift_timing = jobDetail.shift_timing,
        should_show_last_contacted = jobDetail.should_show_last_contacted,
        status = jobDetail.status,
        tags = jobDetail.tags,
        title = jobDetail.title,
        type = jobDetail.type,
        updated_on = jobDetail.updated_on,
        videos = jobDetail.videos,
        views = jobDetail.views,
        whatsapp_no = jobDetail.whatsapp_no
    )
}

fun fromEntity(entity: JobDetailEntity): JobDetail {
    return JobDetail(
        id = entity.id,
        advertiser = entity.advertiser,
        amount = entity.amount,
        button_text = entity.button_text,
        city_location = entity.city_location,
        company_name = entity.company_name,
        contact_preference = entity.contact_preference,
        contentV3 = entity.contentV3,
        created_on = entity.created_on,
        creatives = entity.creatives,
        custom_link = entity.custom_link.toString(),
        enable_lead_collection = entity.enable_lead_collection,
        experience = entity.experience,
        expire_on = entity.expire_on,
        fb_shares = entity.fb_shares,
        fee_details = entity.fee_details,
        fees_charged = entity.fees_charged,
        fees_text = entity.fees_text,
        is_applied = entity.is_applied,
        is_bookmarked = entity.is_bookmarked,
        is_job_seeker_profile_mandatory = entity.is_job_seeker_profile_mandatory,
        is_owner = entity.is_owner,
        is_premium = entity.is_premium,
        job_category = entity.job_category,
        job_category_id = entity.job_category_id,
        job_hours = entity.job_hours,
        job_location_slug = entity.job_location_slug,
        job_role = entity.job_role,
        job_role_id = entity.job_role_id,
        job_tags = entity.job_tags,
        job_type = entity.job_type,
        locality = entity.locality,
        locations = entity.locations,
        num_applications = entity.num_applications,
        openings_count = entity.openings_count,
        other_details = entity.other_details,
        premium_till = entity.premium_till.toString(),
        primary_details = entity.primary_details,
        qualification = entity.qualification,
        question_bank_id = entity.question_bank_id.toString(),
        salary_max = entity.salary_max,
        salary_min = entity.salary_min,
        screening_retry = entity.screening_retry.toString(),
        shares = entity.shares,
        shift_timing = entity.shift_timing,
        should_show_last_contacted = entity.should_show_last_contacted,
        status = entity.status,
        tags = entity.tags,
        title = entity.title,
        type = entity.type,
        updated_on = entity.updated_on,
        videos = entity.videos,
        views = entity.views,
        whatsapp_no = entity.whatsapp_no
    )
}

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromContactPreference(contactPreference: ContactPreference): String {
        return gson.toJson(contactPreference)
    }

    @TypeConverter
    fun toContactPreference(json: String): ContactPreference {
        return gson.fromJson(json, ContactPreference::class.java)
    }

    @TypeConverter
    fun fromContentV3(contentV3: ContentV3): String {
        return gson.toJson(contentV3)
    }

    @TypeConverter
    fun toContentV3(json: String): ContentV3 {
        return gson.fromJson(json, ContentV3::class.java)
    }

    @TypeConverter
    fun fromCreativeList(creatives: List<Creative>): String {
        return gson.toJson(creatives)
    }

    @TypeConverter
    fun toCreativeList(json: String): List<Creative> {
        val type: Type = object : TypeToken<List<Creative>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromFeeDetails(feeDetails: FeeDetails): String {
        return gson.toJson(feeDetails)
    }

    @TypeConverter
    fun toFeeDetails(json: String): FeeDetails {
        return gson.fromJson(json, FeeDetails::class.java)
    }

    @TypeConverter
    fun fromJobTagList(jobTags: List<JobTag>): String {
        return gson.toJson(jobTags)
    }

    @TypeConverter
    fun toJobTagList(json: String): List<JobTag> {
        val type: Type = object : TypeToken<List<JobTag>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromLocationList(locations: List<Location>): String {
        return gson.toJson(locations)
    }

    @TypeConverter
    fun toLocationList(json: String): List<Location> {
        val type: Type = object : TypeToken<List<Location>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromPrimaryDetails(primaryDetails: PrimaryDetails): String {
        return gson.toJson(primaryDetails)
    }

    @TypeConverter
    fun toPrimaryDetails(json: String): PrimaryDetails {
        return gson.fromJson(json, PrimaryDetails::class.java)
    }

    @TypeConverter
    fun fromAnyList(list: List<Any>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toAnyList(json: String): List<Any> {
        val type: Type = object : TypeToken<List<Any>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromAny(value: Any?): String {
        return if (value == null) "" else gson.toJson(value)
    }

    @TypeConverter
    fun toAny(json: String): Any? {
        return if (json.isEmpty()) null else gson.fromJson(json, Any::class.java)
    }
}