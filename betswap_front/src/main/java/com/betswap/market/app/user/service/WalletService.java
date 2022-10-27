package com.betswap.market.app.user.service;


import com.betswap.market.client.wallet.vo.cmd.RegisterWalleAddresstCmd;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalletCmd;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalletKeyCmd;
import com.betswap.market.client.wallet.vo.cmd.WithdrawalWalletCmd;
import com.betswap.market.infrastruture.common.response.ServerResponse;

public interface WalletService {

    ServerResponse generateMnemonicWords();

    ServerResponse generateTrxPathAndMnemonic(RegisterWalletCmd cmd);

    ServerResponse importByPrivateKey(RegisterWalletKeyCmd cmd);

    ServerResponse exportPrivateKey(String userId, String moneyPassword);

    ServerResponse exportMnemonicWords(String userId, String moneyPassword);

    ServerResponse importByAddress(RegisterWalleAddresstCmd cmd);

    ServerResponse findAddressById(String eagueMatchId);

    ServerResponse withdrawal(String userId, WithdrawalWalletCmd cmd);

    ServerResponse findWithdrawalType();
}

