package cn.siyue.platform.weixin.common.service;

import cn.siyue.platform.weixin.common.dto.weixin.card.request.consumecode.ConsumeCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype.Card;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode.CreateCardQrcodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.deletecard.DeleteCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.getusercardlist.GetCardListReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.batchget.BatchGetReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.code.GetCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.CreateCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.getcard.GetCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.modifystock.ModifyStockReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.batchget.BatchGetResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.code.CodeResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.consumecode.ConsumeCodeResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.createcard.CardInfoResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.createcardqrcode.CreateCardQrcodeResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.getusercardlist.GetCardListResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.setselfconsumecell.SetSelfConsumeCellReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.settestwhitelist.SetTestWhiteListReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.unavailablecardcode.UnavailableCardCodeReq;
import cn.siyue.platform.weixin.common.response.coupon.ColorResponse;
import com.fasterxml.jackson.databind.JsonNode;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;

import java.io.File;
import java.util.List;

public interface CardService {

    public WxMediaImgUploadResult uploadImg(File file);

    public CardInfoResult create(CreateCardReq req) throws WxErrorException;

    public CodeResult getCode(GetCodeReq req) throws WxErrorException;

    public Card getCard(GetCardReq req) throws WxErrorException;

    public BatchGetResult batchGet(BatchGetReq req) throws WxErrorException;

    public GetCardListResult getUserCardList(GetCardListReq req) throws WxErrorException;

    public WxMpMassSendResult sendByOpenId(List<String> openIds, String cardId);

    public CreateCardQrcodeResult createCardQrcode(CreateCardQrcodeReq req) throws WxErrorException;

    public String setTestWhiteList(SetTestWhiteListReq req);

    public List<ColorResponse> getColorList();

    public String setSelfConsumeCell(SetSelfConsumeCellReq req);

    public ConsumeCodeResult consumeCode(ConsumeCodeReq req) throws WxErrorException;

    public void modifyStock(ModifyStockReq req) throws WxErrorException;

    public void deleteCard(DeleteCardReq req) throws WxErrorException;

    public void unavailableCardCode(UnavailableCardCodeReq req) throws WxErrorException;
}
