package com.jnu.youownme;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.haibin.calendarview.Calendar;
import com.jnu.youownme.dataprocessor.InDataBank;
import com.jnu.youownme.dataprocessor.Person;

import java.util.List;

public class InListFragment extends Fragment{
    private InDataBank indataBank;
    private InAdapter adapter;
    Calendar calendar=new Calendar();
    private static final int CONTEXT_MENU_ITEM_UPDATE = 1;
    private static final int CONTEXT_MENU_ITEM_DELETE = CONTEXT_MENU_ITEM_UPDATE+1;
    public static final int RESULT_OK_IN = 101;
    private int position1;

    public InListFragment() {
        // Required empty public constructor
    }

    public void indatabank(String person_name,String in_number,String in_reason,String date) {
        indataBank.getIn().add(new Person(person_name,in_number,in_reason,date));
        indataBank.Save();
        adapter.notifyDataSetChanged();
    }

    public static InListFragment newInstance(String param1, String param2) {
        InListFragment fragment = new InListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_in_list, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        adapter = new InAdapter(this.getContext(), R.layout.item_in,  indataBank.getIn());
        ListView listViewIn=view.findViewById(R.id.listview_in);
        listViewIn.setAdapter(adapter);
        this.registerForContextMenu(listViewIn);
    }

    private void initData() {
        indataBank=new InDataBank(this.getContext());
        indataBank.Load();
        if(0==indataBank.getIn().size())
        {
            indataBank.getIn().add(new Person("李某某","200","金榜题名","2021.01.12"));
            indataBank.getIn().add(new Person("高某某","600","金榜题名","2020.12.12"));
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if(v==this.getActivity().findViewById(R.id.listview_in)) {
            menu.setHeaderTitle("操作");
            menu.add(1, CONTEXT_MENU_ITEM_UPDATE, 1, "修改");
            menu.add(1, CONTEXT_MENU_ITEM_DELETE, 1, "删除");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case RESULT_OK_IN:
                if (resultCode == RESULT_OK_IN) {
                    String person_name = data.getStringExtra("person_name");
                    String in_number = data.getStringExtra("in_number");
                    String in_reason = data.getStringExtra("in_reason");
                    String date_in = data.getStringExtra("date");

                    indataBank.getIn().get(position1).setName(person_name);
                    indataBank.getIn().get(position1).setNumber(in_number);
                    indataBank.getIn().get(position1).setReason(in_reason);
                    indataBank.getIn().get(position1).setDate(date_in);
                    indataBank.Save();
                    adapter.notifyDataSetChanged();
                }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent intent_in;
        final int position=menuInfo.position;
        position1=position;
        switch(item.getItemId())
        {
            case CONTEXT_MENU_ITEM_UPDATE:
                intent_in = new Intent(this.getContext(), AddActivity.class);
                intent_in.putExtra("person_name",indataBank.getIn().get(position).getName());
                intent_in.putExtra("in_number",indataBank.getIn().get(position).getNumber());
                intent_in.putExtra("in_reason",indataBank.getIn().get(position).getReason());
                intent_in.putExtra("date",indataBank.getIn().get(position).getDate());
                startActivityForResult(intent_in, RESULT_OK_IN);
                break;

            case CONTEXT_MENU_ITEM_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("询问");
                builder.setMessage("你确定要删除吗？");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        indataBank.getIn().remove(position);
                        indataBank.Save();
                        adapter.notifyDataSetChanged();
                    }
                });  //正面的按钮（肯定）
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }); //反面的按钮（否定)
                builder.create().show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private class InAdapter extends ArrayAdapter<Person> {
        private int resourceId;

        public InAdapter(@NonNull Context context, int resource, @NonNull List<Person> objects) {
            super(context, resource,objects);
            this.resourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Person person = getItem(position);//获取当前项的实例
            View view;
            if(null==convertView)
                view = LayoutInflater.from(getContext()).inflate(this.resourceId, parent, false);
            else
                view=convertView;
            ((TextView) view.findViewById(R.id.view_name_in)).setText(person.getName());
            ((TextView) view.findViewById(R.id.view_number_in)).setText(person.getNumber());
            ((TextView) view.findViewById(R.id.view_reason_in)).setText(person.getReason());
            ((TextView) view.findViewById(R.id.view_date_in)).setText(person.getDate());
            return view;
        }
    }
}
