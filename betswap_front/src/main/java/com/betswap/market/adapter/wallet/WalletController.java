package com.betswap.market.adapter.wallet;


import com.betswap.market.app.user.service.WalletService;
import com.betswap.market.client.user.vo.CurrentUserVO;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalletCmd;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalletKeyCmd;
import com.betswap.market.client.wallet.vo.cmd.WithdrawalWalletCmd;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.CurrentUser;
import com.betswap.market.infrastruture.config.annotation.DisableToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/wallet")
@Api(value = "WalletController", tags = { "/wallet 钱包管理API" })
public class WalletController {
    @Autowired
    private WalletService walletService;



    @ApiOperation(value = "生成12个助记词")
    @DisableToken
    @PostMapping(value = "/generateMnemonicWords")
    public ServerResponse generateMnemonicWords(){
        return walletService.generateMnemonicWords();
    }


    @ApiOperation(value = "根据助记词创建账户/导入账户")
    @DisableToken
    @PostMapping(value = "/generateTrxPathAndMnemonic")
    public ServerResponse generateTrxPathAndMnemonic(@Valid RegisterWalletCmd cmd){
        return walletService.generateTrxPathAndMnemonic(cmd);
    }


    @ApiOperation(value = "根据私钥导入账户")
    @DisableToken
    @PostMapping(value = "/importByPrivateKey")
    public ServerResponse importByPrivateKey(@Valid RegisterWalletKeyCmd cmd){
        return walletService.importByPrivateKey(cmd);
    }



    @ApiOperation(value = "导出私钥")
    @PostMapping(value = "/exportPrivateKey")
    public ServerResponse exportPrivateKey(@CurrentUser CurrentUserVO currentUser,@RequestParam String moneyPassword){
        return walletService.exportPrivateKey(currentUser.getUserId(),moneyPassword);
    }

    @ApiOperation(value = "导出助记词")
    @PostMapping(value = "/exportMnemonicWords")
    public ServerResponse exportMnemonicWords(@CurrentUser CurrentUserVO currentUser,@RequestParam String moneyPassword){
        return walletService.exportMnemonicWords(currentUser.getUserId(),moneyPassword);
    }


    @ApiOperation(value = "提现")
    @PostMapping(value = "/withdrawal")
    public ServerResponse withdrawal(@CurrentUser CurrentUserVO currentUser,@Valid WithdrawalWalletCmd cmd){
        return walletService.withdrawal(currentUser.getUserId(),cmd);
    }

    @ApiOperation(value = "获取提现类型集合")
    @PostMapping(value = "/findWithdrawalType")
    public ServerResponse findWithdrawalType(@CurrentUser CurrentUserVO currentUser){
        return walletService.findWithdrawalType();
    }

}