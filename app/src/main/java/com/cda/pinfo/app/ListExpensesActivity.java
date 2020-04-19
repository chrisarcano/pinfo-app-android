package com.cda.pinfo.app;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cda.pinfo.app.dto.ExpenseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListExpensesActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    public static class RVAdapater extends RecyclerView.Adapter<RVViewHolder> {

        //private ExpenseDTO [] expenses;
        private List<ExpenseDTO> expenses;

        //public RVAdapater(ExpenseDTO [] expenses){
        public RVAdapater(List<ExpenseDTO> expenses){
            this.expenses = expenses;
        }

        @NonNull
        @Override
        public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
            RVViewHolder vh = new RVViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView.findViewById(R.id.item_expense_expenseName);
            tv.setText(expenses.get(position).getExpenseName());
        }

        @Override
        public int getItemCount() {
            return expenses.size();
        }
    }

    public static class RVViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemView;
        public RVViewHolder(@NonNull LinearLayout itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        recyclerView = (RecyclerView) findViewById(R.id.expenses_rview);
        recyclerView.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvLayoutManager);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
            StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Request request = new Request.Builder().url("http://10.0.2.2:8080/expense").build();
        try{
            Response response = client.newCall(request).execute();
            ObjectMapper mapper = new ObjectMapper();
            String responseraw = response.body().string();
            System.out.println("responseraw: " + responseraw);
            List<ExpenseDTO> expenses = mapper.readValue(responseraw,new TypeReference<List<ExpenseDTO>>(){});
            //ExpenseDTO [] expenses = mapper.readValue(responseraw,ExpenseDTO[].class);
            rvAdapter = new RVAdapater(expenses);
            recyclerView.setAdapter(rvAdapter);
            System.out.println("response: " + expenses);
        }catch(Exception e){
            System.out.println("exception: " + e.getMessage());
        }
    }
}
