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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.haibin.calendarview.Calendar;
import com.jnu.youownme.dataprocessor.OutDataBank;
import com.jnu.youownme.dataprocessor.Person;

import java.util.List;


public class OutListFragment extends Fragment {

    private OutDataBank outdataBank;
    private OutAdapter adapter;
    Calendar calendar = new Calendar();

    private static final int CONTEXT_MENU_ITEM_UPDATE = 1;
    private static final int CONTEXT_MENU_ITEM_DELETE = CONTEXT_MENU_ITEM_UPDATE + 1;
    public static final int RESULT_OK_OUT = 100;
    private int position2;


    public OutListFragment() {
        // Required empty public constructor
    }

    public void outdatabank(String person_name, String out_number, String out_reason, String date) {
        outdataBank.getOut().add(new Person(person_name, out_number, out_reason, date));
        outdataBank.Save();
        adapter.notifyDataSetChanged();
    }

    public static OutListFragment newInstance(String param1, String param2) {
        OutListFragment fragment = new OutListFragment();
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
        View view = inflater.inflate(R.layout.fragment_out_list, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        adapter = new OutAdapter(this.getContext(), R.layout.item_out, outdataBank.getOut());

        ListView listViewOut = view.findViewById(R.id.listview_out);
        listViewOut.setAdapter(adapter);

        this.registerForContextMenu(listViewOut);
    }

    private void initData() {
        outdataBank = new OutDataBank(this.getContext());
        outdataBank.Load();
        if (0 == outdataBank.getOut().size()) {
            outdataBank.getOut().add(new Person("李某某", "200", "金榜题名", "2021.01.02"));
            outdataBank.getOut().add(new Person("何某某", "800", "结婚大喜", "2020.12.31"));
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v == this.getActivity().findViewById(R.id.listview_out)) {
            menu.setHeaderTitle("操作");
            menu.add(1, CONTEXT_MENU_ITEM_UPDATE, 1, "修改");
            menu.add(1, CONTEXT_MENU_ITEM_DELETE, 1, "删除");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case RESULT_OK_OUT:
                if (resultCode == RESULT_OK_OUT) {
                    String person_name = data.getStringExtra("person_name");
                    String out_number = data.getStringExtra("out_number");
                    String out_reason = data.getStringExtra("out_reason");
                    String date_out = data.getStringExtra("date");

                    outdataBank.getOut().get(position2).setName(person_name);
                    outdataBank.getOut().get(position2).setNumber(out_number);
                    outdataBank.getOut().get(position2).setReason(out_reason);
                    outdataBank.getOut().get(position2).setDate(date_out);
                    outdataBank.Save();
                    adapter.notifyDataSetChanged();
                }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent intent_out;
        final int position = menuInfo.position;
        position2=position;
        switch (item.getItemId()) {
            case CONTEXT_MENU_ITEM_UPDATE:
                intent_out = new Intent(this.getContext(), AddActivity.class);
                intent_out.putExtra("person_name", outdataBank.getOut().get(position).getName());
                intent_out.putExtra("out_number", outdataBank.getOut().get(position).getNumber());
                intent_out.putExtra("out_reason", outdataBank.getOut().get(position).getReason());
                intent_out.putExtra("date", outdataBank.getOut().get(position).getDate());
                startActivityForResult(intent_out, RESULT_OK_OUT);
                break;

            case CONTEXT_MENU_ITEM_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("询问");
                builder.setMessage("你确定要删除吗？");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        outdataBank.getOut().remove(position);
                        outdataBank.Save();
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

    private class OutAdapter extends ArrayAdapter<Person> {
        private int resourceId;

        public OutAdapter(@NonNull Context context, int resource, @NonNull List<Person> objects) {
            super(context, resource, objects);
            this.resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Person person = getItem(position);//获取当前项的实例
            View view;
            if (null == convertView)
                view = LayoutInflater.from(getContext()).inflate(this.resourceId, parent, false);
            else
                view = convertView;
            ((TextView) view.findViewById(R.id.view_name_out)).setText(person.getName());
            ((TextView) view.findViewById(R.id.view_number_out)).setText(person.getNumber());
            ((TextView) view.findViewById(R.id.view_reason_out)).setText(person.getReason());
            ((TextView) view.findViewById(R.id.view_date_out)).setText(person.getDate());
            return view;
        }
    }
}
