package com.example.android.exemploappidioma_fragments;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class PalavraAdapter extends ArrayAdapter<PalavrasClass> {

    private int mIdBackgroundColor;

    //Para usar um tipo de "ArrayAdapter" que funciona com uma view personalizada com um objeto personalizado, criamos uma classe que herda "extends" um ArrayAdapter e criamos um novo getView pra ela.

    //Criando um construtor, ele mostra o que o objeto criado com o PalavraAdapter vai receber de entrada, que é o "this"(Contexto) e nosso array personalizado.
    //Não criaremos outros métodos novos como no PalavrasClass, só alteraremos o getView.
    public PalavraAdapter(Activity context, ArrayList<PalavrasClass> palavra, int colorId) {
        // Aqui, inicializamos o armazenamento interno do ArrayAdapter para o contexto e a lista.
        // o segundo argumento é usado quando o ArrayAdapter está preenchendo um único TextView.
        // Como este é um adaptador personalizado para dois TextViews e um ImageView
        // vai usar este segundo argumento, então pode ser qualquer valor. Aqui, usamos 0.
        super(context, 0, palavra );
        mIdBackgroundColor = colorId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        // Isso tem relação com a verificação de não se ter views para onde direcionar o adapter.
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Pegando os objetos nas posições de "0 ao tamanho do array" e montando as views com eles, o getItem é um método do ArrayAdapter e como herdamos, podemos utilizá-lo.
        PalavrasClass palavrasAtual = getItem(position);

        // Encontrando o TextView na list_item.xml layout
        TextView ptTextView = (TextView) listItemView.findViewById(R.id.padrao_text_view);

        // Pegando a palavra de palavrasAtual e setando a textView para mostrá - la (Palavra em português).
        ptTextView.setText(palavrasAtual.getPtTraducao());


        // Encontrando o TextView na list_item.xml layout
        TextView miTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Pegando a palavra de palavrasAtual e setando a textView para mostrá - la (Palavra em outro Idioma).
        miTextView.setText(palavrasAtual.getOutraTraducao());

        //Declarando a imageView
        ImageView imagem = (ImageView) listItemView.findViewById(R.id.imageView);

        //Verificando se tem imagem para mostrar na lista
        if(palavrasAtual.temImagem()==true) {
            imagem.setImageResource(palavrasAtual.getImagensId());
            imagem.setVisibility(View.VISIBLE);
        }
        else {
            imagem.setVisibility(View.GONE);
        }

        //Declara o layout onde estão as duas textViews (LinearLayout)
        View layoutTraduçoes = listItemView.findViewById(R.id.layoutTraduçoes);

        //Pra transformar o R.color numa entrada válida para o setBackground tem de fazer essa conversão
        int color = ContextCompat.getColor(getContext(), mIdBackgroundColor);

        //Muda a cor do plano de fundo
        layoutTraduçoes.setBackgroundColor(color);


        //Retorna a lista completa montando a View com palavra em português - palavra em outra língua, imagem(se tiver),
        // o gerenciamento de áudio é feito em cada fragmento e não aqui.
        return listItemView;
    }
}
