package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultsActivity extends AppCompatActivity {
    private CursorAdapter mCursorAdapter;
    private ListView mListView, mListViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
      //  mListViewMain = (ListView) findViewById(R.id.listview_main); //this is where the full list should display before you search
        mListView = (ListView) findViewById(R.id.listview_results);
      //  mListView.setEmptyView(findViewById(R.id.no_results_text));

        if(Intent.ACTION_SEARCH.equals(getIntent().getAction())){

            String query =getIntent().getStringExtra(SearchManager.QUERY);

            Cursor cursor = ShoppingSQLiteOpenHelper.getInstance(this).searchShoppingList(query);

            mCursorAdapter = new CursorAdapter(this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
                }

                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    int index = cursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_NAME);
                    textView.setText(cursor.getString(index));

                }
            };
            mListView.setAdapter(mCursorAdapter);
        }





    }
}
