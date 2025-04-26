package com.shekhar.lokal.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shekhar.lokal.navigation.DetailScreen
import com.shekhar.lokal.viewmodel.JobViewModel

@Composable
fun HomePage(navController: NavController, viewmodel: JobViewModel, modifier: Modifier) {

    val isLoading by viewmodel.isLoading.collectAsStateWithLifecycle()
    val isLoadingNext by viewmodel.isLoadingNext.collectAsStateWithLifecycle()
    val showRetry by viewmodel.showRetry.collectAsStateWithLifecycle()
    val jobs by viewmodel.jobList.collectAsStateWithLifecycle()

    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.Green, modifier = Modifier.size(30.dp))
        }
    } else if (showRetry) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Something went wrong",
                color = Color.Red,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Retry",
                modifier = Modifier.clickable {
                    viewmodel.getJobs()
                    viewmodel.resetRetry()
                },
                color = Color.Green
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(jobs) { index, job ->

                if (jobs.isNotEmpty() && index >= jobs.size - 2) {
                    viewmodel.getJobsNext()
                }

                if (job.title != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewmodel.selectJob(job)
                                navController.navigate(DetailScreen(job.id))
                            },
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = job.title,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Location: ${job.primary_details.Place}",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Salary: ${job.primary_details.Salary}",
                                fontSize = 14.sp,
                                color = Color.Green
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Contact Details: ${job.whatsapp_no}",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            item {
                AnimatedVisibility(isLoadingNext) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = Color.Green)
                    }
                }
            }
        }
    }
}

