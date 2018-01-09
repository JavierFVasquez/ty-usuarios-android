package com.imaginamos.usuariofinal.taxisya.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.imaginamos.usuariofinal.taxisya.Holders.ChatHolder;
import com.imaginamos.usuariofinal.taxisya.Model.Chat;
import com.imaginamos.usuariofinal.taxisya.R;

import java.util.HashMap;
import java.util.Map;


public class ChatActivity extends AppCompatActivity {

    private RecyclerView RV_Chat_Content;
    private EditText ET_Chat_Text;
    private Button BT_Send;
    private String service_id;
    private RecyclerView.Adapter adapter;
    private boolean first_time = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        service_id = getIntent().getExtras().getString("service_id","0");

        RV_Chat_Content = (RecyclerView) findViewById(R.id.RV_Chat_Content);
        ET_Chat_Text = (EditText) findViewById(R.id.ET_Chat_Text);
        BT_Send = (Button) findViewById(R.id.BT_Send);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chat_ref = database.getReference("chat/taxi_user/"+service_id);
        Query queryRef = chat_ref.orderByChild("service_id").equalTo(service_id);


        RV_Chat_Content.setHasFixedSize(false);
        RV_Chat_Content.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        adapter = new FirebaseRecyclerAdapter<Chat, ChatHolder>(Chat.class, R.layout.item_chat, ChatHolder.class, queryRef) {
            @Override
            public void populateViewHolder(ChatHolder ChatHolder, final Chat chat, int position) {


                switch (chat.getTypeId()){
                    case 0:
                        ChatHolder.getTV_Message_Text_Taxi().setText(chat.getMessage());
                        ChatHolder.getRL_Message_Container_User().setVisibility(View.GONE);
                        break;
                    default :
                        ChatHolder.getTV_Message_Text_User().setText(chat.getMessage());
                        ChatHolder.getRL_Message_Container_Taxi().setVisibility(View.GONE);
                        break;
                }


            }
        };
        RV_Chat_Content.setAdapter(adapter);
        LinearLayoutManager lm = (LinearLayoutManager) RV_Chat_Content.getLayoutManager();
        lm.setStackFromEnd(true);

        chat_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(first_time) {
                    RV_Chat_Content.scrollToPosition(RV_Chat_Content.getAdapter().getItemCount() - 1);
                    Log.i("Chat", "New Message");
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                }
                first_time = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: " , String.valueOf(databaseError.getCode()));
            }
        });
        RV_Chat_Content.setLayoutManager(lm);

        BT_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                first_time = false;

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference chat_ref = database.getReference();
                String key = chat_ref.push().getKey();


                Chat new_message = new Chat();

                new_message.setMessage(ET_Chat_Text.getText().toString().trim());
                new_message.setTypeId(1);
                new_message.setService_id(service_id);



                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/chat/taxi_user/"+service_id+ "/"+ key, new_message);
                chat_ref.updateChildren(childUpdates);

                ET_Chat_Text.setText("");
            }
        });


    }

}
