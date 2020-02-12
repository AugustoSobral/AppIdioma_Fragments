package com.example.android.exemploappidioma_fragments;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamiliaFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;     //Gerenciamento do foco de áudio.

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            //Perder o foco de áudio temporariamente
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // O aplicativo recuperou o foco de áudio novamente.
                mMediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // O aplicativo perdeu o foco de áudio definitivamente.
                releaseMediaPlayer();
            }
        }
    };

    public FamiliaFragment() {
        // Construtor público vazio
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_familia, container, false);

        mAudioManager =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Usando ArrayList para salvar os números de um à dez e suas traduções, foi criado uma nova classe PalavrasClass que dá origem a objetos que armazenam duas Strings.

        final ArrayList<PalavrasClass> palavras = new ArrayList<PalavrasClass>();




        palavras.add(new PalavrasClass ("Pai","әpә", R.drawable.family_father, R.raw.family_father));
        palavras.add(new PalavrasClass ("Mãe","әṭa", R.drawable.family_mother, R.raw.family_mother));
        palavras.add(new PalavrasClass ("Filho","angsi", R.drawable.family_son, R.raw.family_son));
        palavras.add(new PalavrasClass ("Filha","tune", R.drawable.family_daughter, R.raw.family_daughter));
        palavras.add(new PalavrasClass ("Irmão mais velho","taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        palavras.add(new PalavrasClass ("Irmão mais novo","chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        palavras.add(new PalavrasClass ("Irmã mais velha","teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        palavras.add(new PalavrasClass ("Irmã mais nova","kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        palavras.add(new PalavrasClass ("Avó","ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        palavras.add(new PalavrasClass ("Avô","paapa", R.drawable.family_grandfather, R.raw.family_grandfather));




        PalavraAdapter itemsAdapter = new PalavraAdapter(getActivity(), palavras, R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        //Configurando o onItemClickListener para tocar o áudio de cada item.

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PalavrasClass itensAtual = palavras.get(position);

                releaseMediaPlayer();

                // Requisita o foco de áudio para tocar o áudio
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Usa Stream Music.
                        AudioManager.STREAM_MUSIC,
                        // Request áudio por um período curto de tempo, audios de 1,2 segundos.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                //Se o foco de áudio foi requisitado com sucesso:
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Começa a tocar

                    mMediaPlayer = MediaPlayer.create(getActivity(), itensAtual.getAudioId());
                    mMediaPlayer.start();

                    //Método que executa uma ação quando o áudio acaba de tocar (libera memória no caso).
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
        return rootView;
    }

    //Quando o app é minimizado, liberamos a memória dos áudios também.
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // // Método que libera a memória usada para gerenciar os áudios
        if (mMediaPlayer != null) {
            // Liberamos os recursos de memória

            mMediaPlayer.release();

            // Definimos mMedia player como nulo para mostrar que os recursos estão liberados no momento
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);  //Abandonar o foco de áudio.
        }
    }
}
