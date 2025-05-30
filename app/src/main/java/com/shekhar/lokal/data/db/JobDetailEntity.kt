package com.shekhar.lokal.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shekhar.lokal.data.model.ContactPreference
import com.shekhar.lokal.data.model.ContentV3
import com.shekhar.lokal.data.model.Creative
import com.shekhar.lokal.data.model.FeeDetails
import com.shekhar.lokal.data.model.JobTag
import com.shekhar.lokal.data.model.Location
import com.shekhar.lokal.data.model.PrimaryDetails

@Entity(tableName = "job_details")
data class JobDetailEntity(
    @PrimaryKey
    val id: Int,
    val advertiser: Int,
    val amount: String,
    val button_text: String,
    val city_location: Int,
    val company_name: String,
    val contact_preference: ContactPreference,
    val contentV3: ContentV3,
    val created_on: String,
    val creatives: List<Creative>,
    val custom_link: String?,
    val enable_lead_collection: Boolean,
    val experience: Int,
    val expire_on: String,
    val fb_shares: Int,
    val fee_details: FeeDetails,
    val fees_charged: Int,
    val fees_text: String,
    val is_applied: Boolean,
    val is_bookmarked: Boolean,
    val is_job_seeker_profile_mandatory: Boolean,
    val is_owner: Boolean,
    val is_premium: Boolean,
    val job_category: String,
    val job_category_id: Int,
    val job_hours: String,
    val job_location_slug: String,
    val job_role: String,
    val job_role_id: Int,
    val job_tags: List<JobTag>,
    val job_type: Int,
    val locality: Int,
    val locations: List<Location>,
    val num_applications: Int,
    val openings_count: Int,
    val other_details: String,
    val premium_till: String?,
    val primary_details: PrimaryDetails,
    val qualification: Int,
    val question_bank_id: Any?,
    val salary_max: Int,
    val salary_min: Int,
    val screening_retry: Any?,
    val shares: Int,
    val shift_timing: Int,
    val should_show_last_contacted: Boolean,
    val status: Int,
    val tags: List<Any>,
    val title: String?,
    val type: Int,
    val updated_on: String,
    val videos: List<Any>,
    val views: Int,
    val whatsapp_no: String
)
