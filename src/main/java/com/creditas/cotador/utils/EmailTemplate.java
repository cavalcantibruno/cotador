package com.creditas.cotador.utils;

import ch.qos.logback.core.util.StringUtil;
import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@NoArgsConstructor
public final class EmailTemplate {

    public static String emailDeSimulacao(SimulacaoResponseDto info, SimulacaoRequestDto req) {
        String nome = StringUtil.isNullOrEmpty(req.getNome()) ? "Cliente" : req.getNome();

        // Formatação brasileira para moeda, mas poderiamos utilizar outras localidades que funcionaria
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        NumberFormat percentFormat = NumberFormat.getPercentInstance(new Locale("pt", "BR"));
        percentFormat.setMinimumFractionDigits(2);

        String valorSolicitado = currencyFormat.format(info.getValorSolicitado());
        String valorParcela = currencyFormat.format(info.getValorParcelaMensal());
        String valorTotal = currencyFormat.format(info.getValorTotalComJuros());
        String taxaJuros = String.format("%.2f%%", info.getTaxaJuros());

        return String.format("""
        <html>
        <body style="font-family: Arial, sans-serif; color: #333;">
            <p>Olá <strong>%s</strong>!</p>

            <p>Temos novidades incríveis pra você! 🎉<br>
            A simulação do seu empréstimo foi realizada com sucesso e já temos todos os detalhes que você precisa para tomar a melhor decisão. 💰🚀</p>

            <p>Dá só uma olhada nas condições que preparamos especialmente pra você:</p>

            <ul>
                <li><strong>🔹 Valor solicitado:</strong> %s</li>
                <li><strong>🔹 Número de parcelas:</strong> %dx</li>
                <li><strong>🔹 Taxa de juros anual:</strong> %s</li>
                <li><strong>🔹 Valor de cada parcela:</strong> %s</li>
                <li><strong>🔹 Valor total a ser pago:</strong> %s</li>
            </ul>

            <p>Tudo isso com transparência, segurança e a agilidade que você merece. 😊</p>

            <p>Se estiver tudo certo, é só clicar no botão abaixo para seguir com a contratação:</p>

            <p>
                👉 <a href="#" style="background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Finalizar contratação</a>
            </p>

            <p>Estamos aqui pra te ajudar a realizar seus planos!</p>
        </body>
        </html>
        """,
                nome,
                valorSolicitado,
                info.getPrazo(),
                taxaJuros,
                valorParcela,
                valorTotal
        );
    }

}
