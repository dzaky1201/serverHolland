package com.dzakyhdr.hollandbakeryserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.dzakyhdr.hollandbakeryserver.EventBus.AddonSizeEditEvent;
import com.dzakyhdr.hollandbakeryserver.EventBus.SelectAddonModel;
import com.dzakyhdr.hollandbakeryserver.EventBus.SelectSizeModel;
import com.dzakyhdr.hollandbakeryserver.EventBus.UpdateAddonModel;
import com.dzakyhdr.hollandbakeryserver.EventBus.UpdateSizeModel;
import com.dzakyhdr.hollandbakeryserver.adapter.MyAddonAdapter;
import com.dzakyhdr.hollandbakeryserver.adapter.MySizeAdapter;
import com.dzakyhdr.hollandbakeryserver.common.Common;
import com.dzakyhdr.hollandbakeryserver.model.AddonModel;
import com.dzakyhdr.hollandbakeryserver.model.SizeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SizeAddonEditActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_price)
    EditText edt_price;
    @BindView(R.id.btn_create)
    Button btn_create;
    @BindView(R.id.btn_edit)
    Button btn_edit;
    @BindView(R.id.recycler_addon_size)
    RecyclerView recycler_addon_size;

    //variable
    MySizeAdapter adapter;
    MyAddonAdapter addonAdapter;
    private int foodEditPosition = -1;
    private boolean needSave = false;
    private boolean isAddon = false;

    //event
    @OnClick(R.id.btn_create)
    void onCreateNew()
    {
        if (!isAddon)
        {
            if (adapter != null)
            {
                SizeModel sizeModel = new SizeModel();
                sizeModel.setName(edt_name.getText().toString());
                sizeModel.setPrice(Long.valueOf(edt_price.getText().toString()));
                adapter.addNewSize(sizeModel);
            }
        }
        else
        {
            if (addonAdapter != null)
            {
                AddonModel addonModel = new AddonModel();
                addonModel.setName(edt_name.getText().toString());
                addonModel.setPrice(Long.valueOf(edt_price.getText().toString()));
                addonAdapter.addNewSize(addonModel);
            }
        }
    }

    @OnClick(R.id.btn_edit)
    void onEdit()
    {
        if (!isAddon) //size
        {
            if (adapter != null)
            {
                SizeModel sizeModel = new SizeModel();
                sizeModel.setName(edt_name.getText().toString());
                sizeModel.setPrice(Long.valueOf(edt_price.getText().toString()));
                adapter.editSize(sizeModel);
            }
        }
        else
        {
            if (addonAdapter != null)//addon
            {
                AddonModel addonModel = new AddonModel();
                addonModel.setName(edt_name.getText().toString());
                addonModel.setPrice(Long.valueOf(edt_price.getText().toString()));
                addonAdapter.editSize(addonModel);
            }
        }
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addon_size_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveData();
                break;
            case android.R.id.home:
            {
                if (needSave)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Cancel ?")
                            .setMessage("Apakah yakin ingin keluar tanpa di save ?")
                            .setNegativeButton("CANCEL", (dialogInterface, which) -> dialogInterface.dismiss())
                            .setPositiveButton("OK", (dialog, which) -> {
                                needSave = false;
                                finish();
                                closeActivity();

                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    closeActivity();
                }
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        if (foodEditPosition != -1)
        {
            Common.categorySelected.getFoods().set(foodEditPosition,Common.selectedFood);

            Map<String, Object> updateData = new HashMap<>();
            updateData.put("foods",Common.categorySelected.getFoods());

            FirebaseDatabase.getInstance()
                    .getReference(Common.CATEGORY_REF)
                    .child(Common.categorySelected.getMenu_id())
                    .updateChildren(updateData)
                    .addOnFailureListener(e -> Toast.makeText(SizeAddonEditActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(this, "Reload Success !", Toast.LENGTH_SHORT).show();
                            needSave = false;
                            edt_price.setText("0");
                            edt_name.setText("");
                        }
                    });
        }
    }

    private void closeActivity() {
        edt_name.setText("");
        edt_price.setText("0");
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_addon_edit);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recycler_addon_size.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_addon_size.setLayoutManager(layoutManager);
        recycler_addon_size.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().removeStickyEvent(UpdateSizeModel.class);
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onStop();
    }

    //receive event
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onAddonSizeReceive(AddonSizeEditEvent event) {
        if (!event.isAddonn()) // if event is size
        {
            if (Common.selectedFood.getSize() != null) // if size is not empty
            {
                adapter = new MySizeAdapter(this, Common.selectedFood.getSize());
                foodEditPosition = event.getPos();//save food edit to update
                recycler_addon_size.setAdapter(adapter);

                isAddon = event.isAddonn();
            }
        }
        else //is addon
        {
            if (Common.selectedFood.getAddon() != null) // if addon is not empty
            {
                addonAdapter = new MyAddonAdapter(this, Common.selectedFood.getAddon());
                foodEditPosition = event.getPos();//save food edit to update
                recycler_addon_size.setAdapter(addonAdapter);

                isAddon = event.isAddonn();
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSizeModelUpdate(UpdateSizeModel event) {
        if (event.getSizeModelList() != null) {
            needSave = true;
            Common.selectedFood.setSize(event.getSizeModelList());
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onAddonModelUpdate(UpdateAddonModel event) {
        if (event.getAddonModel() != null) {
            needSave = true;
            Common.selectedFood.setAddon(event.getAddonModel());
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSelectSizeModel(SelectSizeModel event) {
        if (event.getSizeModel() != null)
        {
            edt_name.setText(event.getSizeModel().getName());
            edt_price.setText(String.valueOf(event.getSizeModel().getPrice()));

            btn_edit.setEnabled(true);
        }
        else
        {
            btn_edit.setEnabled(false);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSelectAddonModel(SelectAddonModel event) {
        if (event.getAddonModel() != null)
        {
            edt_name.setText(event.getAddonModel().getName());
            edt_price.setText(String.valueOf(event.getAddonModel().getPrice()));

            btn_edit.setEnabled(true);
        }
        else
        {
            btn_edit.setEnabled(false);
        }
    }
}
