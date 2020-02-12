package com.example.android.exemploappidioma_fragments;

public class PalavrasClass {
                                                                                //Estados
    private String mPtTraducao;

    private String mMiTraducao;

    private static final int SEM_IMAGEM = -1;

    private int mImagensId = SEM_IMAGEM;

    private int mAudioResourceId;
                                                                                //Construtor 1
    public PalavrasClass(String PtTraducao, String MiTraducao, int audioResourceId){
        mPtTraducao = PtTraducao;
        mMiTraducao = MiTraducao;
        mAudioResourceId = audioResourceId;

    }

    //Construtor 2 //Se o construtor 1 for chamado, a variável da imagem continua com o -1 e eu uso isso pra
    // verificar se há imagem ou não em outras atividades, porque em "Frases" não há imagens.

    public PalavrasClass(String PtTraducao, String MiTraducao, int ImagensId, int audioResourceId){
        mPtTraducao = PtTraducao;
        mMiTraducao = MiTraducao;
        mImagensId = ImagensId;
        mAudioResourceId = audioResourceId;
    }
                                                                                //Métodos que retornam os dados do objeto
    public String getPtTraducao(){                                              //Pega a palavra em Pt
        return mPtTraducao;
    }
    public String getOutraTraducao(){                                              //Pega a palavra em Mi
        return mMiTraducao;
    }
    public int getImagensId(){
        return mImagensId;
    }
    public boolean temImagem(){
        if (mImagensId==SEM_IMAGEM)
            return false;
        else
            return true;
    }
    public int getAudioId(){ return mAudioResourceId; }
}
