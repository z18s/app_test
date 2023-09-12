package com.example.testapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.testapp.R
import com.example.testapp.application.App
import com.example.testapp.model.retrofit.RetrofitConnection
import com.example.testapp.model.room.Cache
import com.example.testapp.presenter.IMainPresenter
import com.example.testapp.presenter.MainPresenter
import com.example.testapp.view.ui.theme.TestAppTheme

class MainActivity : ComponentActivity(), IMainView {

    companion object {
        private const val MAIN_TAG = "main_text"
    }

    private lateinit var presenter: IMainPresenter

    private lateinit var mutableTextState: MutableState<String>
    private lateinit var mutableListState: MutableState<List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MainPresenter(RetrofitConnection(App.getInstance().dataSource), Cache(App.getInstance().database))
        presenter.attachView(this)

        setContent {
            val text = savedInstanceState?.getString(MAIN_TAG) ?: ""
            val list = emptyList<String>()
            mutableTextState = remember { mutableStateOf(text) }
            mutableListState = remember { mutableStateOf(list) }

            presenter.update()

            TestAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(mutableTextState, mutableListState)
                }
            }
        }
    }

    @Composable
    fun MainScreen(textState: MutableState<String>, listState: MutableState<List<String>>) {

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val mainPaddings = PaddingValues(dimensionResource(R.dimen.main_paddings))
            val textFieldHeight = dimensionResource(R.dimen.text_field_height)
            val spacerHeight = dimensionResource(R.dimen.spacer_height)

            Text(
                text = textState.value,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(mainPaddings)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .height(textFieldHeight)
            )

            Row {
                Button(
                    modifier = Modifier
                        .padding(mainPaddings)
                        .fillMaxWidth()
                        .weight(0.7f),
                    onClick = { presenter.onQueryClick() },
                    ) {
                    Text(text = stringResource(id = R.string.button_text))
                }
                Button(
                    modifier = Modifier
                        .padding(mainPaddings)
                        .fillMaxWidth()
                        .weight(0.3f),
                    onClick = { presenter.onDeleteClick() }) {
                    Text(text = stringResource(id = R.string.button_delete_text))
                }
            }

            LazyColumn() {
                items(items = listState.value) {text ->
                    HistoryItem(text = text)
                    Spacer(modifier = Modifier.height(spacerHeight))
                }
            }
        }
    }

    @Composable
    private fun HistoryItem(text: String) {
        val listPaddings = PaddingValues(dimensionResource(R.dimen.list_paddings))
        val shapeCorner = dimensionResource(R.dimen.list_shape_corner)

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.95f)
                .clip(shape = RoundedCornerShape(shapeCorner))
                .background(Color.LightGray)
                .padding(listPaddings)
        )
    }

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        mutableTextState = remember { mutableStateOf("Test") }
        mutableListState = remember { mutableStateOf(listOf("1", "2", "3", "4", "5")) }

        TestAppTheme {
            MainScreen(mutableTextState, mutableListState)
        }
    }

    override fun setText(text: String) {
        mutableTextState.value = text
    }

    override fun setHistory(list: List<String>) {
        mutableListState.value = list
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MAIN_TAG, mutableTextState.value)
        super.onSaveInstanceState(outState)
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}