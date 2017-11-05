package com.example.android.todomanager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.todomanager.db.TodoDbHelper;
import com.example.android.todomanager.db.tables.TodoTable;
import com.example.android.todomanager.models.Todo;

import java.util.ArrayList;

import static android.R.attr.name;
import static android.content.ContentValues.TAG;
import static com.example.android.todomanager.R.id.cb;
import static com.example.android.todomanager.db.tables.TodoTable.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvTodos;
    EditText etNewTodo;
    Button btnAddTodo;
    Button btnDelTodo;
    public static final String TAG = "hi";
    ArrayList<Todo> todos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTodos = (RecyclerView) findViewById(R.id.rvTodos);
        etNewTodo = (EditText) findViewById(R.id.etNewTodo);
        btnAddTodo = (Button) findViewById(R.id.btnAddTodo);
        btnDelTodo = (Button) findViewById(R.id.btnDelTodo);
        final SQLiteDatabase todoDb = new TodoDbHelper(this).getWritableDatabase();
        todos = TodoTable.getAllTodos(todoDb);

        final TodoAdapter todoArrayAdapter = new TodoAdapter();
        rvTodos.setLayoutManager(new LinearLayoutManager(this));
        rvTodos.setAdapter(todoArrayAdapter);


        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long todoId = TodoTable.insertTodo(
                        todoDb,
                        new Todo(etNewTodo.getText().toString(), false)
                );
               // Log.d(FragmentActivity.TAG, "onClick: " + todoId);

                todos = TodoTable.getAllTodos(todoDb);
               // Log.e(TAG, "onClick: ", );
                /*for(int i =0 ;i<todos.size();i++){
                    if(etNewTodo.getText().toString()==todos.get(i).getTask())
                }*/
                todoArrayAdapter.notifyDataSetChanged();
            }
        });

        btnDelTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              TodoTable.deleteTodo(todoDb);
                todos= TodoTable.getAllTodos(todoDb);
                todoArrayAdapter.notifyDataSetChanged();
            }
        });
    }

class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoViewHolder(getLayoutInflater().inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, final int position) {
        holder.text1.setText(todos.get(position).getTask());
        //holder.cb.setChecked(false);

        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean x1=false;
                Log.e(TAG, "onClick: "+todos.get(holder.getAdapterPosition()).getId()+holder.cb.isChecked()+" "+todos.get(position).isDone());
                final SQLiteDatabase todoDb = new TodoDbHelper(MainActivity.this).getWritableDatabase();
                if(holder.cb.isChecked()){
                    x1= true;
                } else {
                    x1= false;
                }
                int x = TodoTable.updateTodo(todoDb, new Todo(todos.get(position).getTask(), x1), position);
                todos = TodoTable.getAllTodos(todoDb);
                notifyDataSetChanged();
                Log.e(TAG,"onClick "+todos.get(position).isDone());
            }

        });
    }

    @Override
    public int getItemCount() {
        //Log.d(TAG, "getItemCount: " + todos.size());
        return todos.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        CheckBox cb;
        public TodoViewHolder(View itemView) {
            super(itemView);
            cb= itemView.findViewById(R.id.cb);
            //cb.setChecked(false);
            text1 = itemView.findViewById(R.id.textView);
        }
    }
}

}
