package com.poly.thi_ph51025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.poly.thi_ph51025.R
import com.poly.thi_ph51025.ui.theme.Thi_PH51025_2504Theme

//b6
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isSplashDone by remember { mutableStateOf(false) }

            if (!isSplashDone) {
                SplashScreen {
                    isSplashDone = true
                }
            } else {
                MonThiScreen(MonThiViewModel())
            }
        }
    }
}
@Composable
fun DetailDialog(
    MonThi: MonThi,
    onDismiss: () -> Unit,
) {
    androidx.compose.material.AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Product Detail")
        },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "Môn thi: " +MonThi.monThi,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "Họ tên: " + MonThi.hoTen,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Ngày thi: " +MonThi.ngayThi,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "Ca thi: " +MonThi.caThi,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                AsyncImage(
                    model = MonThi.hinhAnh,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(100.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterVertically)
                )

            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun MonThiScreen(
    viewModel: MonThiViewModel
) {
    val lstMonThi by viewModel.MonThi.observeAsState()
    val selected by viewModel.SLMonThi.observeAsState()
    // Hiển thị Dialog khi có sản phẩm được chọn
    selected?.let {
        DetailDialog(MonThi = it, onDismiss = { viewModel.clearSelected() })
    }
    LazyColumn(
        modifier = Modifier.padding(8.dp).fillMaxSize()
    ) {
        items(lstMonThi ?: emptyList()) { MonThi->
            ItemMonThi(
                monThi = MonThi,
                onItemClick = { id -> viewModel.getDetail(id.id)  }
            )
        }
    }
}


@Composable
fun ItemMonThi(
    monThi: MonThi,
    onItemClick: (MonThi) -> Unit,
) {
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = { onItemClick(monThi) }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize().background(color = Color.Cyan),
        ) {
            Column (
                modifier = Modifier
                    .padding(end = 8.dp)
            ){
                Text(
                    text = "Họ tên: " + monThi.hoTen,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Môn thi: " +monThi.monThi,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Ngày thi: " +monThi.ngayThi,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Ca thi: " +monThi.caThi,
                    modifier = Modifier.padding(8.dp)
                )            }

            AsyncImage(
                model = monThi.hinhAnh,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 80.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
                    .border(5.dp, Color.Gray, CircleShape)
            )
        }
    }
}

@Composable
fun SplashScreen(onContinue: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    var maSV by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    if (showError) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar("Sai mã sinh viên! Vui lòng nhập lại.")
            showError = false
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo FPT",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(5.dp, Color.Green, CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = maSV,
                onValueChange = { maSV = it },
                label = { Text("Mã sinh viên") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (maSV.trim().lowercase() == "ph51025") {
                    onContinue()
                } else {
                    showError = true
                }
            }) {
                Text("Tiếp tục")
            }
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Thi_PH51025_2504Theme {
        Greeting("Android")
    }
}