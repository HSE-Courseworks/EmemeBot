package ru.mamakapa.vkbot.bot.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.events.Events;
import com.vk.api.sdk.objects.audio.Audio;
import com.vk.api.sdk.objects.board.TopicComment;
import com.vk.api.sdk.objects.callback.BoardPostDelete;
import com.vk.api.sdk.objects.callback.GroupChangePhoto;
import com.vk.api.sdk.objects.callback.GroupChangeSettings;
import com.vk.api.sdk.objects.callback.GroupJoin;
import com.vk.api.sdk.objects.callback.GroupLeave;
import com.vk.api.sdk.objects.callback.GroupOfficersEdit;
import com.vk.api.sdk.objects.callback.MarketComment;
import com.vk.api.sdk.objects.callback.MarketCommentDelete;
import com.vk.api.sdk.objects.callback.MessageAllow;
import com.vk.api.sdk.objects.callback.MessageDeny;
import com.vk.api.sdk.objects.callback.PhotoComment;
import com.vk.api.sdk.objects.callback.PhotoCommentDelete;
import com.vk.api.sdk.objects.callback.PollVoteNew;
import com.vk.api.sdk.objects.callback.UserBlock;
import com.vk.api.sdk.objects.callback.UserUnblock;
import com.vk.api.sdk.objects.callback.VideoComment;
import com.vk.api.sdk.objects.callback.VideoCommentDelete;
import com.vk.api.sdk.objects.callback.WallCommentDelete;
import com.vk.api.sdk.objects.callback.messages.CallbackMessage;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.video.Video;
import com.vk.api.sdk.objects.wall.WallComment;
import com.vk.api.sdk.objects.wall.Wallpost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EventsHandler {
    private static final String OVERRIDING_ERR = "Method of handling event is not overridden";
    private static final Logger LOG = LoggerFactory.getLogger(EventsHandler.class);
    protected final Gson gson = new Gson();

    public EventsHandler() {
    }

    private <T> T designateObject(JsonObject object, Events type) {
        return this.gson.fromJson(object, type.getType());
    }

    protected String confirmation() {
        LOG.error(OVERRIDING_ERR);
        return null;
    }

    protected void messageNew(Integer groupId, Message message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void messageReply(Integer groupId, Message message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void messageEdit(Integer groupId, Message message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void messageAllow(Integer groupId, MessageAllow message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void messageDeny(Integer groupId, MessageDeny message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void photoNew(Integer groupId, Photo message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void photoCommentNew(Integer groupId, PhotoComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void photoCommentEdit(Integer groupId, PhotoComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void photoCommentRestore(Integer groupId, PhotoComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void photoCommentDelete(Integer groupId, PhotoCommentDelete message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void audioNew(Integer groupId, Audio message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void videoNew(Integer groupId, Video message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void videoCommentNew(Integer groupId, VideoComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void videoCommentEdit(Integer groupId, VideoComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void videoCommentRestore(Integer groupId, VideoComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void videoCommentDelete(Integer groupId, VideoCommentDelete message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void wallPostNew(Integer groupId, Wallpost message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void wallRepost(Integer groupId, Wallpost message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void wallReplyNew(Integer groupId, WallComment object) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void wallReplyEdit(Integer groupId, WallComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void wallReplyRestore(Integer groupId, WallComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void wallReplyDelete(Integer groupId, WallCommentDelete message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void boardPostNew(Integer groupId, TopicComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void boardPostEdit(Integer groupId, TopicComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void boardPostRestore(Integer groupId, TopicComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void boardPostDelete(Integer groupId, BoardPostDelete message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void marketCommentNew(Integer groupId, MarketComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void marketCommentEdit(Integer groupId, MarketComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void marketCommentRestore(Integer groupId, MarketComment message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void marketCommentDelete(Integer groupId, MarketCommentDelete message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void groupLeave(Integer groupId, GroupLeave message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void groupJoin(Integer groupId, GroupJoin message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void groupChangeSettings(Integer groupId, GroupChangeSettings message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void groupChangePhoto(Integer groupId, GroupChangePhoto message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void groupOfficersEdit(Integer groupId, GroupOfficersEdit message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void pollVoteNew(Integer groupId, PollVoteNew message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void userBlock(Integer groupId, UserBlock message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void userUnblock(Integer groupId, UserUnblock message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected String parse(CallbackMessage message) {
        switch (message.getType()) {
            case CONFIRMATION -> {
                return this.confirmation();
            }
            case MESSAGE_NEW -> this.messageNew(message.getGroupId(),
                    gson.fromJson(message.getObject().getAsJsonObject("message"), message.getType().getType()));
            case MESSAGE_REPLY ->
                    this.messageReply(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case MESSAGE_EDIT ->
                    this.messageEdit(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case MESSAGE_ALLOW ->
                    this.messageAllow(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case MESSAGE_DENY ->
                    this.messageDeny(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case PHOTO_NEW ->
                    this.photoNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case PHOTO_COMMENT_NEW ->
                    this.photoCommentNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case PHOTO_COMMENT_EDIT ->
                    this.photoCommentEdit(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case PHOTO_COMMENT_RESTORE ->
                    this.photoCommentRestore(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case PHOTO_COMMENT_DELETE ->
                    this.photoCommentDelete(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case AUDIO_NEW ->
                    this.audioNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case VIDEO_NEW ->
                    this.videoNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case VIDEO_COMMENT_NEW ->
                    this.videoCommentNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case VIDEO_COMMENT_EDIT ->
                    this.videoCommentEdit(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case VIDEO_COMMENT_RESTORE ->
                    this.videoCommentRestore(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case VIDEO_COMMENT_DELETE ->
                    this.videoCommentDelete(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case WALL_POST_NEW ->
                    this.wallPostNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case WALL_REPOST ->
                    this.wallRepost(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case WALL_REPLY_NEW ->
                    this.wallReplyNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case WALL_REPLY_EDIT ->
                    this.wallReplyEdit(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case WALL_REPLY_RESTORE ->
                    this.wallReplyRestore(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case WALL_REPLY_DELETE ->
                    this.wallReplyDelete(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case BOARD_POST_NEW ->
                    this.boardPostNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case BOARD_POST_EDIT ->
                    this.boardPostEdit(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case BOARD_POST_RESTORE ->
                    this.boardPostRestore(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case BOARD_POST_DELETE ->
                    this.boardPostDelete(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case MARKET_COMMENT_NEW ->
                    this.marketCommentNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case MARKET_COMMENT_EDIT ->
                    this.marketCommentEdit(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case MARKET_COMMENT_RESTORE ->
                    this.marketCommentRestore(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case MARKET_COMMENT_DELETE ->
                    this.marketCommentDelete(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case GROUP_LEAVE ->
                    this.groupLeave(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case GROUP_JOIN ->
                    this.groupJoin(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case GROUP_CHANGE_SETTINGS ->
                    this.groupChangeSettings(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case GROUP_CHANGE_PHOTO ->
                    this.groupChangePhoto(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case GROUP_OFFICERS_EDIT ->
                    this.groupOfficersEdit(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case USER_BLOCK ->
                    this.userBlock(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case USER_UNBLOCK ->
                    this.userUnblock(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            case POLL_VOTE_NEW ->
                    this.pollVoteNew(message.getGroupId(), this.designateObject(message.getObject(), message.getType()));
            default -> {
                LOG.error("Unexpected callback event type received");
                return null;
            }
        }

        return "OK";
    }
}
