package com.poly.thi_ph51025_2504

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.poly.thi_ph51025_2504.ui.theme.Thi_PH51025_2504Theme
import kotlinx.coroutines.launch

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
            .padding(20.dp),
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
                        text = MonThi.hoTen,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = MonThi.monThi,
                        modifier = Modifier.padding(8.dp)
                    )

                    Text(
                        text = MonThi.ngayThi,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                AsyncImage(
                    model = MonThi.hinhAnh,
                    contentDescription = "null",
                    modifier = Modifier.padding(8.dp).size(100.dp)
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
                MonThi = MonThi,
                onItemClick = { id -> viewModel.getDetail(id.id)  }
            )
        }
    }
}


@Composable
fun ItemMonThi(
    MonThi: MonThi,
    onItemClick: (MonThi) -> Unit,
) {
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = { onItemClick(MonThi) }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
        ) {
            Column (
                modifier = Modifier
                    .padding(end = 8.dp)
            ){
                Text(
                    text = "Họ tên: " + MonThi.hoTen,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Môn thi: " +MonThi.monThi,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Ngày thi: " +MonThi.ngayThi,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Ca thi: " +MonThi.caThi,
                    modifier = Modifier.padding(8.dp)
                )            }
            AsyncImage(
                model = MonThi.hinhAnh,
                contentDescription = "null",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(80.dp)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape)
                    .border(5.dp, Color.Gray, CircleShape)
            )
        }
    }
}
@Composable
fun SplashScreen(onContinue: () -> Unit) {
    var maSV by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Circle Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(5.dp, Color.Gray, CircleShape)
        )
        Text(text = "Nhập mã sinh viên", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = maSV,
            onValueChange = {
                maSV = it
                showError = false
            },
            label = { Text("Mã sinh viên") },
            isError = showError
        )

        if (showError) {
            Text("Sai mã sinh viên!", color = Color.Red)
        }

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