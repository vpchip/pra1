package com.example.wimo.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wimo.HistoryActivity;
import com.example.wimo.PrivacyInfo;
import com.example.wimo.R;
import com.example.wimo.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Adapter adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);

        ArrayList<PrivacyInfo> list = new ArrayList<>();
        if (getActivity() instanceof HistoryActivity) {
            HistoryActivity activity = (HistoryActivity) getActivity();
            list.addAll(activity.getPrivacyInfoList());
        }
        adapter.setList(list);

        return view;
    }

    private void onItemClicked(String address) {
        Utils.showDialogFragment(getFragmentManager(), "DIALOG_MAP", DialogMap.newInstance(address));
    }

    private static class Adapter extends RecyclerView.Adapter<Adapter.VHItem> {
        private final WeakReference<ListFragment> mFragment;
        private final ArrayList<PrivacyInfo> mList = new ArrayList<>();

        Adapter(ListFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        void setList(ArrayList<PrivacyInfo> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public VHItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_calendar,parent,false);
            //v.setBackgroundColor(Color.RED);//추가 리스트 전체 배경 색상 변경
            System.out.println("다른곳"+mList.get(0).getLocation());
            if (mList.get(0).getLocation().equals("1600 Amphitheatre Pkwy, Mountain View, CA 94043 미국\n")){

            }
            return new VHItem(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VHItem holder, int position) {
            PrivacyInfo info = mList.get(position);
            holder.textTime.setText(info.getTime());
            holder.textLocation.setText(info.getLocation().trim());
            //holder.textTime.setBackgroundColor(Color.rgb(255,0,0)); //추가 추후 주소 같을때 불린값 불러와서 if문으로 색 변경
            //holder.textLocation.setBackgroundColor(Color.rgb(255,0,0));//추가 추후 주소 같을때 불린값 불러와서 if문으로 색 변경
            //System.out.println(mList.get(0).getTime()); //추가 리스트뷰 0번째 시간 가져오기
            //System.out.println(mList.size()); //추가 리스트뷰(mList)에 저장된 총 아이템 수
            for (int i=0;i<mList.size();i++){
               // System.out.println("리스트"+(i+1)+"번"); //추가 리스트뷰 순서 출력
              //  System.out.println(mList.get(i).getTime()); //추가 리스트뷰 순서에 해당하는 시간 출력
              //  System.out.println(mList.get(i).getLocation()); //추가 리스트뷰 순서에 해당하는 위치 출력
              //  if (mList.get(i).getTime() == "2021-11-21 18:04:44"){ //추가 시간 비교해서 해당하는 포지션만 색상 변경
              //      System.out.println("포지션"+mList.get(position)); //추가 색상 변경 코드 또는 onCreateViewHolder에 전달
              //  }

                String a ="1600 Amphitheatre Pkwy, Mountain View, CA 94043 미국\n"; //추가 주의! 리스트 값 마지막에는 \n이 들어감
                System.out.println(a);
                if (mList.get(i).getLocation().equals(a)){  //추가 mList의 위치 값과 특정 위치값(현재 스트링 a) 같을 경우
                    holder.textTime.setBackgroundColor(Color.GREEN); //추가 배경 색상 변경
                }
                //if (position % 2 == 0){ //추가 포지션(리스트의 순서) 값에 따라 색상 변경 코드 추후 시간 또는 위치 비교 코드 삽입
                //   holder.textTime.setBackgroundColor(Color.YELLOW); //추가 리스트의 시간 색상 변경 코드
               // }
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class VHItem extends RecyclerView.ViewHolder{
            TextView textTime;
            TextView textLocation;
            VHItem(final View itemView) {
                super(itemView);
                textTime = itemView.findViewById(R.id.text_time);
                textLocation = itemView.findViewById(R.id.text_location);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PrivacyInfo info = mList.get(getAdapterPosition());
                        if (mFragment.get() != null) mFragment.get().onItemClicked(info.getLocation().trim());
                    }
                });
            }
        }
    }

}
