package br.edu.unisenai.controleepi.adapter;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.edu.unisenai.controleepi.R;
import java.util.*;

public abstract class RegistroAdapter<T> extends RecyclerView.Adapter<RegistroAdapter.Holder> {
  protected final List<T> itens = new ArrayList<>();

  public void substituir(List<T> novos) {
    itens.clear();
    itens.addAll(novos);
    notifyDataSetChanged();
  }

  @NonNull
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
    return new Holder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registro, parent, false));
  }

  public void onBindViewHolder(@NonNull Holder h, int pos) {
    h.acao.setVisibility(View.GONE);
    h.acao.setEnabled(true);
    h.acao.setOnClickListener(null);
    vincular(h, itens.get(pos));
  }

  protected abstract void vincular(Holder h, T item);

  public int getItemCount() {
    return itens.size();
  }

  public static class Holder extends RecyclerView.ViewHolder {
    public final TextView titulo, detalhe;
    public final Button acao;

    public Holder(View v) {
      super(v);
      titulo = v.findViewById(R.id.tituloItem);
      detalhe = v.findViewById(R.id.detalheItem);
      acao = v.findViewById(R.id.acaoItem);
    }
  }
}
