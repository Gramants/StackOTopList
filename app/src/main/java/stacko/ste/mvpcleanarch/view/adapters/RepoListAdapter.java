package stacko.ste.mvpcleanarch.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;

import java.util.ArrayList;
import java.util.List;

import stacko.ste.mvpcleanarch.R;
import stacko.ste.mvpcleanarch.model.entities.ItemDbEntity;


public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {

    private final List<ItemDbEntity> items;
    private final OnItemClickListener listener;

    public void clearAll() {
        this.items.clear();
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(ItemDbEntity item);

        void setBlockedStatusAs(boolean isOn, ItemDbEntity item);

        void setFollowStatusAs(boolean isOn, ItemDbEntity item);

        void showToastIsBlocked();
    }

    public RepoListAdapter(OnItemClickListener listener) {
        this.items = new ArrayList();
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(items.get(position), listener, holder.itemView.getContext());


    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void updateRepoList(List<ItemDbEntity> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mReputation;
        private ImageView mImage;
        private LabeledSwitch mFollow;
        private LabeledSwitch mBlocked;
        private RelativeLayout mContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            mContainer = (RelativeLayout) itemView.findViewById(R.id.container);
            mTitle = (TextView) itemView.findViewById(R.id.tv_name);
            mReputation = (TextView) itemView.findViewById(R.id.tv_reputation);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mBlocked = (LabeledSwitch) itemView.findViewById(R.id.blocked);
            mFollow = (LabeledSwitch) itemView.findViewById(R.id.follow);
        }

        public void bindItem(ItemDbEntity item, OnItemClickListener listener, Context ctx) {
            mTitle.setText(item.getDisplayName());
            mReputation.setText(ctx.getResources().getString(R.string.reputation, String.valueOf(item.getReputation())));
            Glide.with(ctx)
                    .load(item.getProfileImage())
                    .into(mImage);
            mBlocked.setActivated(false);
            mFollow.setActivated(false);
            mBlocked.setOn(item.getBlocked());
            mFollow.setOn(item.getFollowing());
            mBlocked.setActivated(true);
            mFollow.setActivated(true);


            if (item.getBlocked()) {
                mContainer.setBackgroundColor(ctx.getResources().getColor(R.color.grey));
                mContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.showToastIsBlocked();
                    }
                });
            } else {

                if (item.getFollowing()) {
                    mContainer.setBackgroundColor(ctx.getResources().getColor(R.color.follow));
                } else {
                    mContainer.setBackgroundColor(ctx.getResources().getColor(R.color.colorPrimary));
                }

                mContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(item);
                    }
                });

            }


            mFollow.setOnToggledListener(new OnToggledListener() {
                @Override
                public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                    listener.setFollowStatusAs(isOn, item);
                }
            });

            mBlocked.setOnToggledListener(new OnToggledListener() {
                @Override
                public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                    listener.setBlockedStatusAs(isOn, item);
                }
            });


        }
    }


}