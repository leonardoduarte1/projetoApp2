package com.example.projetoapp2.mvp.Home

import com.example.projetoapp2.mvp.BasePresenter
import com.example.projetoapp2.mvp.BaseView

/**
 * No contrato podemos definir todos os métodos que são mandatórios
 * de implementação pelas camadas View e Presenter de cada uma de nossas
 * Activities ou Fragments
 */
interface MainContract {

    /**
     * Nossa LoginActivity precisa implementar os métodos definidos abaixo
     */
    interface View : BaseView<MainPresenter> {
    }

    /**
     * Nosso Presenter precisa implementar os seguintes métodos
     */
    interface Presenter : BasePresenter {
        fun atualizarValorCarteira()
    }
}