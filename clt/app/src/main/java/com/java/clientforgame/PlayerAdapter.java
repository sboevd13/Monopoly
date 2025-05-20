package com.java.clientforgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private List<String> players;

    public PlayerAdapter(List<String> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.nicknameText.setText(players.get(position));
    }

    @Override
    public int getItemCount() {
        return players != null ? players.size() : 0;
    }

    public void updatePlayers(List<String> newPlayers) {
        this.players = newPlayers;
        notifyDataSetChanged();
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView nicknameText;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            nicknameText = itemView.findViewById(R.id.nicknameText);
        }
    }
}
