package com.shekhar.lokal.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shekhar.lokal.viewmodel.JobViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.shekhar.lokal.R
import com.shekhar.lokal.navigation.formatDate
import com.shekhar.lokal.navigation.toEntity


@Composable
fun JobDetailPage(navController: NavController, viewModel: JobViewModel, jobId: Int) {

    val job by viewModel.selectedJob.collectAsStateWithLifecycle()
    val bookmarkStatus by viewModel.isBookmark.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        viewModel.bookMarkStatus(jobId)
        onDispose { viewModel.resetSelectedJob() }
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Job Details",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(10.dp)
        ) {
            item {

                job?.creatives?.firstOrNull()?.file?.let { imageUrl ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Job Thumbnail",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 10.dp),
                        placeholder = painterResource(R.drawable.img),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Title + Bookmark button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        job?.let {
                            it.title?.let { it1 ->
                                Text(
                                    text = it1,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 10.dp),
                                    fontSize = 20.sp
                                )
                            }
                        }

                        Button(
                            onClick = {
                                if (bookmarkStatus) {
                                    viewModel.clearBookmark(jobId)
                                } else {
                                    job?.let { viewModel.addBookmark(toEntity(it)) }
                                }
                            },
                            modifier = Modifier.animateContentSize(),
                            colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Green)
                        ) {
                            Icon(painter = painterResource(R.drawable.bookmark), contentDescription = null)
                            Text(
                                text = if (bookmarkStatus) "Remove" else "BookMark"
                            )
                        }
                    }

                    job?.let {
                        it.job_role?.let { Text(text = it, fontSize = 22.sp) } // Job Role
                        it.company_name?.let { Text(text = "Company: $it") } // Company Name
                        it.primary_details?.Place?.let { Text(text = "Location: $it") } // Job Location
                        it.primary_details?.Salary?.let { Text(text = "Salary: $it") } // Salary
                        it.primary_details?.Experience?.let { Text(text = "Experience: $it") } // Experience Required
                        it.primary_details?.Qualification?.let { Text(text = "Qualification: $it") } // Qualification Required
                    }


                    job?.let {
                        it.id.let { Text(text = "Job ID: $it") }
                        it.job_category.let { Text(text = "Category: $it") }
                        it.other_details.let { Text(text = it) }
                        it.openings_count.let { Text(text = "Openings: $it") }
                        it.num_applications?.let { Text(text = "Applicants: $it") }


                        it.contentV3?.V3?.get(1)?.let { Text(text = "${it.field_key}: ${it.field_value}") }
                        it.contentV3.V3?.get(2)?.let { Text(text = "${it.field_key}: ${it.field_value}") }
                        it.contentV3?.V3?.get(3)?.let { Text(text = "${it.field_key}: ${it.field_value}") }


                        it.created_on?.let { Text(text = "Opening date: ${formatDate(it)}") }
                        it.expire_on?.let { Text(text = "Last date to apply: ${formatDate(it)}") }
                        it.whatsapp_no?.let { Text(text = "Contact Info: $it") }
                        it.contact_preference?.whatsapp_link?.let { Text(text = "Contact Medium: Whatsapp") }


                        it.views?.let { Text(text = "Viewed by $it People") }
                        it.shares?.let { Text(text = "Shared by $it People") }
                        it.amount?.let { Text(text = "Application fee: $it") }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null
) {
    if (navigationIcon != null) {
        TopAppBar(
            title = {
                Text(text = title)
            },
            navigationIcon = navigationIcon,
            actions = {
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
        )
    }
}
