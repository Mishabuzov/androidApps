package com.fsep.sova.network;

import android.support.annotation.Nullable;

import com.fsep.sova.models.AssignTaskSendingModel;
import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Comment;
import com.fsep.sova.models.CommentSendingModel;
import com.fsep.sova.models.Event;
import com.fsep.sova.models.Conversation;
import com.fsep.sova.models.ConversationInfo;
import com.fsep.sova.models.ConversationLabelSendingModel;
import com.fsep.sova.models.CountryCity;
import com.fsep.sova.models.Document;
import com.fsep.sova.models.FavouriteSendingModel;
import com.fsep.sova.models.InvitationSendingModel;
import com.fsep.sova.models.Note;
import com.fsep.sova.models.Post;
import com.fsep.sova.models.PostEvent;
import com.fsep.sova.models.PostOnParticipantEvent;
import com.fsep.sova.models.Photo;
import com.fsep.sova.models.ReceivedInvitation;
import com.fsep.sova.models.Respond;
import com.fsep.sova.models.ResponseOnTask;
import com.fsep.sova.models.Resume;
import com.fsep.sova.models.SentInvitation;
import com.fsep.sova.models.Tag;
import com.fsep.sova.models.TakingTaskSendingModel;
import com.fsep.sova.models.Task;
import com.fsep.sova.models.UpdateUserInfoModel;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.models.Video;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SOVARest {

    //                      РЕГИСТРАЦИЯ И АВТОРИЗАЦИЯ

    /**
     * Авторизация
     *
     * @param nickname Логин
     * @param password Пароль
     */
    @POST("login")
    Call<BaseResponseModel<UserInfo>> login(@Header("NickName") String nickname, @Header("Password") String password);

    /**
     * Проверка корректности данных регистрируемого пользователя
     *
     * @param options содержит:
     *                check_phone - необязательный параметр, Номер телефона пользователя
     *                check_nickname - необязательный параметр, Никнейм пользователя
     * @return isUnique - уникальность введенных данных
     */
    @GET("registration")
    Call<BaseResponseModel> checkRegistration(@QueryMap Map<String, Object> options);

    /**
     * Повторная проверка телефона
     *
     * @param phone - Номер телефона пользователя.
     * @return - в header приходит временный токен авторизации
     */
    @GET("registration")
    Call<BaseResponseModel> confirmPhone(@Query("for_confirm") String phone);

    /**
     * Проверка корректности проверочного кода
     *
     * @param confirmCode - Код подтверждения, по умолчанию - 1234567.
     * @param authToken   - Временный токен авторизации.
     * @return UserInfo - данные, зарегистрированного пользователя.
     */
    @GET("registration")
    Call<BaseResponseModel> confirmCode(@Query("confirm") String confirmCode,
                                        @Header("tempauth-token") String authToken);

    /**
     * Запрос на регистрацию
     *
     * @param authToken - обязательный параметр, Временный токен авторизации.
     * @param body      - обязательный параметр, Информация о пользователе, которого необходимо зарегистрировать.
     */
    @POST("users")
    Call<BaseResponseModel<UserInfo>> registerUser(@Header("TempAuth-Token") String authToken,
                                                   @Body UserInfo body);


    //                            РАБОТА С ЗАДАЧАМИ

    /**
     * Запрос на получение сообственных задач
     *
     * @param options содержит:
     *                published - обязательный параметр, возвращает опубликованные, либо не опубликованные задачи
     *                count - необязательный параметр, показывает кол-во возвращаемых записей
     *                from - необязательный параметр, id с которого начинается отсчет.
     * @return возвращает опубликованные, либо не опубликованные задачи в виде модели Task.
     */
    @GET("my/author/tasks")
    Call<BaseResponseModel<List<Task>>> getOwnTasks(@QueryMap Map<String, Object> options);

    /**
     * Запрос на получение списка выполняемых задач
     *
     * @param options содержит:
     *                count - необязательный параметр, показывает кол-во возвращаемых записей
     *                from - необязательный параметр, id с которого начинается отсчет.
     * @return возвращает список задач в виде модели Task
     */
    @GET("my/performer/tasks")
    Call<BaseResponseModel<List<Task>>> getPerformingTasks(@QueryMap Map<String, Object> options);


    /**
     * Отказаться от исполнения задачи
     *
     * @param taskId - ид задачи
     */
    @DELETE("my/performer/tasks/{task-id}")
    Call<BaseResponseModel> declineTask(@Path("task-id") long taskId);

    /**
     * Снять задачу с исполнителя
     *
     * @param taskId - Идентификатор задачи
     * @param userId - Идентификатор пользователя
     */
    @DELETE("users/{user-id}/performer/tasks/{task-id}")
    Call<BaseResponseModel> kickUser(@Path("task-id") long taskId,
                                     @Path("userId") long userId);

    /**
     * Запрос на получение информации о задаче по id
     *
     * @param taskId id запрашиваемой задачи
     */
    @GET("tasks/{task-id}")
    Call<BaseResponseModel<Task>> getTaskById(@Path("task-id") long taskId);

    /**
     * Создание задачи
     *
     * @param task - Информация о задаче, которую необходимо создать
     */
    @POST("tasks")
    Call<BaseResponseModel<Task>> createTask(@Body Task task);

    /**
     * Обновление задачи
     *
     * @param taskId Идентификатор обновляемой задачи
     * @param task   Обновляемая информация по задаче
     */
    @PUT("tasks/{task-id}")
    Call<BaseResponseModel<Task>> updateTask(@Path("task-id") long taskId, @Nullable @Body Task task);

    /**
     * Удаление задачи
     *
     * @param taskId - Идентификатор задачи
     */
    @DELETE("tasks/{task-id}")
    Call<BaseResponseModel> deleteTask(@Path("task-id") long taskId);


    //                              РАБОТА С ПОСТАМИ

    /**
     * Получение списка постов, котрые были созданы мной
     *
     * @param options - содержит в себе:
     *                published - В зависимости от значения параметра возвращает опубликованные, либо не опубликованные посты
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет;
     * @return List<Post> - созданные мной посты
     */
    @GET("my/author/posts")
    Call<BaseResponseModel<List<Post>>> getPostsCreatedByMe(@QueryMap Map<String, Object> options);

    /**
     * Удалить пост
     *
     * @param postId - Идентификатор поста;
     * @return - status: success/fail
     */
    @DELETE("posts/{post-id}")
    Call<BaseResponseModel> deletePost(@Path("post-id") long postId);

    /**
     * Получить информацию по посту
     *
     * @param postId - Идентификатор поста
     * @return Post - запрашиваемый пост
     */
    @GET("posts/{post-id}")
    Call<BaseResponseModel<Post>> getPostInDetailsById(@Path("post-id") long postId);

    /**
     * Обновление поста
     *
     * @param postId - Идентификатор поста
     * @param post   - Обновляемая информация по посту
     * @return Post - Обновленный пост
     */
    @PUT("posts/{post-id}")
    Call<BaseResponseModel<Post>> refreshPost(@Path("post-id") long postId, @Body Post post);

    /**
     * Создание поста
     *
     * @param post - Информация о посте, который необходимо создать;
     * @return Post - созданный пост;
     */
    @POST("posts")
    Call<BaseResponseModel<Post>> createPost(@Body Post post);

    /**
     * Получение списка постов, котрые были созданы определенным пользователем
     *
     * @param mUserId - Идентификатор пользователя
     * @param options - содержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     * @return List<Post> - Лист постов, созданных юзером с указанным ид;
     */
    @GET("users/{user-id}/posts")
    Call<BaseResponseModel<List<Post>>> getAnotherUserPosts(@Path("user-id") long mUserId, @QueryMap Map<String, Object> options);


    //                              РАБОТА С СОБЫТИЯМИ

    /**
     * Получение списка событий, котрые были созданы мной
     *
     * @param options содержит:
     *                published - В зависимости от значения параметра возвращает опубликованные, либо не опубликованные события
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет.
     * @return List<Event> - созданные мной события
     */
    @GET("my/author/events")
    Call<BaseResponseModel<List<Event>>> getEventsCreatedByMe(@QueryMap Map<String, Object> options);

    /**
     * Создание события
     *
     * @param event - Информация о событии, которое необходимо создать
     * @return Event - Созданное событие
     */
    @POST("events")
    Call<BaseResponseModel<Event>> createNewEvent(@Body PostEvent event);

    /**
     * Удалить событие
     *
     * @param eventId - Идентификатор события
     * @return status: success/fail
     */
    @DELETE("events/{event-id}")
    Call<BaseResponseModel> deleteEvent(@Path("event-id") long eventId);

    /**
     * Получить информацию по событию
     *
     * @param eventId - Идентификатор события
     * @return Event с указанным ид
     */
    @GET("events/{event-id}")
    Call<BaseResponseModel<Event>> getEventById(@Path("event-id") long eventId);

    /**
     * Обновление события
     *
     * @param eventId - Идентификатор события
     * @param event   - Обновляемая информация по событию
     * @return Event - обновлённое событие
     */
    @PUT("events/{event-id}")
    Call<BaseResponseModel<Event>> refreshEvent(@Path("event-id") long eventId,
                                                @Body PostEvent event);

    /**
     * Список участников события
     *
     * @param eventId - Идентификатор события
     * @param options - содержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     * @return List<UserInfo> - список участников события
     */
    @GET("events/{event-id}/participants")
    Call<BaseResponseModel<List<UserInfo>>> getParticipantsOfTheEvent(@Path("event-id") long eventId,
                                                                      @QueryMap Map<String, Object> options);

    /**
     * Получение списка событий, в которых я принимаю участие
     *
     * @param options - содержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     * @return List<Event> - список событий, где я участвую.
     */
    @GET("my/participant/events")
    Call<BaseResponseModel<List<Event>>> getEventsWhereIParticipate(@QueryMap Map<String, Object> options);

    /**
     * Принять участие в событии
     *
     * @param event - Информация о событии, в котором принимаем участие
     * @return event в котором участвуем;
     */
    @POST("my/participant/events")
    Call<BaseResponseModel<Event>> takeParticipationInTheEvent(@Body PostOnParticipantEvent event);

    /**
     * Отменить решение об участии в событии
     *
     * @param eventId - Идентификатор события
     * @return status: success/fail;
     */
    @DELETE("my/participant/events/{event-id}")
    Call<BaseResponseModel> cancelDecisionToParticipateInTheEvent(@Path("event-id") long eventId);


    //                              РАБОТА С ЗАПИСЯМИ

    /**
     * Лайк записи
     *
     * @param noteId - Идентификатор записи
     * @param action - Чтобы сделаь like записи, необходимо указать значение like;
     * @return Note - обновлённая заметка
     */
    @PUT("notes/{note-id}")
    Call<BaseResponseModel<Note>> putLike(@Path("note-id") long noteId, @Query("action") String action);


    //                              COMMENTS

    /**
     * получение всех комментов к задаче
     *
     * @param taskId  - Идентификатор задачи, обязательный парам
     * @param options - содержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     * @return UserInfo model
     */
    @GET("notes/{note-id}/comments")
    Call<BaseResponseModel<List<Comment>>> getAllComments(@Path("note-id") long taskId,
                                                          @QueryMap Map<String, Object> options);

    /**
     * создание нового коммента к задаче
     *
     * @param taskId - обяз. парам - Идентификатор задачи
     * @param body   - обяз. парам - Комментарий, который необходимо создать
     */
    @POST("notes/{note-id}/comments")
    Call<BaseResponseModel<Comment>> createNewComment(@Path("note-id") long taskId,
                                                      @Body CommentSendingModel body);

    /**
     * удаление коммента
     *
     * @param taskId    - обяз. парам - Идентификатор задачи
     * @param commentId - обяз. парам - Идентификатор комментария
     */
    @DELETE("notes/{note-id}/comments/{comment-id}")
    Call<BaseResponseModel> deleteComment(@Path("note-id") long taskId,
                                          @Path("comment-id") long commentId);

    /**
     * обновление коммента
     *
     * @param taskId    - обяз. парам - Идентификатор задачи
     * @param commentId - обяз. парам - Идентификатор комментария
     * @param body      - обяз. парам - Обновляемая информация комментария
     */
    @PUT("notes/{note-id}/comments/{comment-id}")
    Call<BaseResponseModel> updateComment(@Path("note-id") long taskId,
                                          @Path("comment-id") long commentId,
                                          @Body CommentSendingModel body);


    //                            FAVOURITES

    /**
     * Вывод всех избранных задач
     *
     * @param options - сожержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     * @return List of favourite tasks
     */
    @GET("my/favorites")
    Call<BaseResponseModel<List<Note>>> getFavouriteTasks(@QueryMap Map<String, Object> options);

    /**
     * Добавление задачи в избранное
     *
     * @param body - Информация о задаче, которую следует добавить в избранное
     */
    @POST("my/favorites")
    Call<BaseResponseModel> addToFavourites(@Body FavouriteSendingModel body);

    /**
     * Удаление задачи из избранного
     *
     * @param noteId - обязательный парам, Идентификатор записи
     */
    @DELETE("my/favorites/{note-id}")
    Call<BaseResponseModel> deleteFromFavourites(@Path("note-id") long noteId);


    //                              FEED

    /**
     * Получение ленты задач
     *
     * @param options - сожержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     * @return List<Task>
     */
    @GET("my/feed")
    Call<BaseResponseModel<List<Note>>> getFeed(@QueryMap Map<String, Object> options);

    //                             INVITATIONS

    /**
     * Список задач, на которые меня пригласили.
     * Представление задачи.
     *
     * @param options - сожержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     * @return Task, на который пригласили
     */
    @GET("my/invitations")
    Call<BaseResponseModel<List<ReceivedInvitation>>> getReceivedInvitations(@QueryMap Map<String, Object> options);


    /**
     * Отказаться от приглашения
     *
     * @param invitationId - Идентификатор приглашения
     */
    @DELETE("my/invitations/{invitation-id}")
    Call<BaseResponseModel> deleteInvitation(@Path("invitation-id") long invitationId);

    /**
     * Получить всек отправленные приглашения по задаче
     *
     * @param taskId - Идентификатор задачи
     * @return List<SentInvitation> - список отправленных приглашений по задаче
     */
    @GET("tasks/{task-id}/invitations")
    Call<BaseResponseModel<List<SentInvitation>>> getSentInvitations(@Path("task-id") long taskId);


    /**
     * Пригласить пользователя на задачу
     *
     * @param userId - Идентификатор пользователя
     * @param body   - Информация о задаче, на которую мы приглашаем пользователя
     */
    @POST("users/{user-id}/invitations")
    Call<BaseResponseModel> inviteUser(@Path("user-id") long userId,
                                       @Body InvitationSendingModel body);

    /**
     * берем задачу на исполнение
     *
     * @param body - Информация о задаче, которую следует взять на исполнение
     */
    @POST("my/performer/tasks")
    Call<BaseResponseModel<Task>> takeTask(@Body TakingTaskSendingModel body);


    //                            ПОИСК

    /*****
     * Поиск пользователей
     * В случае если поиск выполнялся по никнейму (by=nickname) возвращается не МАССИВ, а ОДИН ОБЪЕКТ
     *
     * @param options - сожержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     *                find - Если значения поля задано, выполняется поиск пользователей, характеристики которых попадают под значение параметра - имя, фамилия, ник
     *                by - Если значение параметра равно nickname, выполняется поиск по никнейму. Если значение поля не задано, поиск выполняется по всем параметрам профиля
     * @return List<UserInfo> - Список найденных пользователей
     */
    @GET("users")
    Call<BaseResponseModel<List<UserInfo>>> findUsers(@QueryMap Map<String, Object> options);

    /*****
     * Поиск задач по ключевому слову
     *
     * @param options - содержит:
     *                find - Если значения поля задано, выполняется поиск задач, характеристики которых попадают под ключевое слово.
     *                count - Количество возвращаемых записей.
     *                from - Указывается ранк записи, с которой следует начать отсчет.
     * @return List<Task> - Список найденных задач
     */
    @GET("tasks")
    Call<BaseResponseModel<List<Task>>> findTasks(@QueryMap Map<String, Object> options);

    /******
     * Поиск тегов по ключевому слову
     *
     * @param options - содержит:
     *                find - Если значения поля задано, выполняется поиск тегов, характеристики которых попадают под ключевое слово.
     *                count - Количество возвращаемых записей.
     *                from - Указывается ранг записи, с которой следует начать отсчет.
     * @return List<Tag> - Список найденных тегов
     */
    @GET("tags")
    Call<BaseResponseModel<List<Tag>>> findTags(@QueryMap Map<String, Object> options);


    //                          РАБОТА С ОТКЛИКАМИ

    /*****
     * Получение списка откликов на задачу
     *
     * @param taskId  - Идентификатор задачи, обязательный парам.
     * @param options - сожержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     * @return ResponseOnTask - как список откликов.
     */
    @GET("tasks/{task-id}/responses")
    Call<BaseResponseModel<List<ResponseOnTask>>> getResponsesOnTask(@Path("task-id") long taskId,
                                                                     @QueryMap Map<String, Object> options);

    /*****
     * Создание отклика на задачу
     *
     * @param body - Отклик
     */
    @POST("my/responses")
    Call<BaseResponseModel<ResponseOnTask>> addResponse(@Body Respond body);

    /******
     * Назначить задачу пользователю
     *
     * @param userId - Идентификатор пользователя
     * @param body   - Информация о задаче, которую следует назначить
     */
    @POST("users/{user-id}/performer/tasks")
    Call<BaseResponseModel> assignTaskToUser(@Path("user-id") long userId,
                                             @Body AssignTaskSendingModel body);

    /*****
     * Отменить отклик
     *
     * @param responseId - Идентификатор отклика
     */
    @DELETE("my/responses/{response-id}")
    Call<BaseResponseModel> declineResponse(@Path("response-id") long responseId);


    //                           РАБОТА С ФАЙЛОВЫМ ХРАНИЛИЩЕМ

    /**
     * Получение Тикета
     *
     * @param reason - обяз. парам, Цель операции - для получения тикета необходимо задать значение to_upload
     * @return String ticket - Тикет для загрузки.
     */
    @GET("uploads/credentials")
    Call<BaseResponseModel> getTicket(@Query("reason") String reason);


    //                             Работа с пользователями

    /**
     * Получить пользователя по id
     *
     * @param userId - id user-a
     * @return UserInfo - инфа о юзере
     */
    @GET("users/{user-id}")
    Call<BaseResponseModel<UserInfo>> getUserById(@Path("user-id") long userId);

    /**
     * Обновить юзерскую инфу
     *
     * @param userId      - id user-a
     * @param updatedUser - инфа о юзере
     * @return UserInfo - инфа о юзере
     */
    @PUT("users/{user-id}")
    Call<BaseResponseModel<UserInfo>> updateUserInfo(@Path("user-id") long userId,
                                                     @Body UpdateUserInfoModel updatedUser);


    //                           РАБОТА С ОБСУЖДЕНИЯМИ

    /**
     * Получение списка сообщений
     *
     * @param taskId  - Идентификатор задачи
     * @param options - содержит:
     *                label - Метка, которой помечено сообщение - возможные значения - needs_review (требуется пересмотр), approved (одобрено), in_progress (в процессе). Если значение метки не задано, то возвращаются все сообщения диалога.
     *                count - Количество возвращаемых записей.
     *                from - Указывается идентификатор записи, с которой следует начать отсчет (запись не включается).
     * @return List<Conversation> - Лист всех отправленных сообщений.
     */
    @GET("tasks/{task-id}/conversation/messages")
    Call<BaseResponseModel<List<Conversation>>> getConversations(@Path("task-id") long taskId,
                                                                 @QueryMap Map<String, Object> options);

    /**
     * sending message
     *
     * @param taskId  - message's id
     * @param message - new message
     * @return full model of new message
     */
    @POST("tasks/{task-id}/conversation/messages")
    Call<BaseResponseModel<Conversation>> sendConversation(@Path("task-id") long taskId,
                                                           @Body Conversation message);

    /**
     * Проставление метки для сообщения.
     * Запрос обрабатывается только в том случае, если пользователь - автор задачи.
     *
     * @param taskId    - Идентификатор задачи.
     * @param messageId - Идентификатор сообщения.
     * @param body      - Метка для сообщения.
     * @return Conversation - Обновлённое сообщение
     */
    @PUT("tasks/{task-id}/conversation/messages/{message-id}")
    Call<BaseResponseModel<Conversation>> putLabelForConversation(@Path("task-id") long taskId,
                                                                  @Path("message-id") long messageId,
                                                                  @Body ConversationLabelSendingModel body);

    /**
     * Получение изображений из всех сообщений
     *
     * @param taskId - Идентификатор задачи
     *               * @param options - содержит:
     *               count - Количество возвращаемых записей.
     *               from - Указывается идентификатор записи, с которой следует начать отсчет (запись не включается).
     * @return List<Photo> - Все изображения из всех сообщений
     */
    @GET("tasks/{task-id}/conversation/images")
    Call<BaseResponseModel<List<Photo>>> getAllImagesFromAllConversations(@Path("task-id") long taskId,
                                                                          @QueryMap Map<String, Object> options);

    /**
     * Получение всех видеозаписей из всех сообщений
     *
     * @param taskId - Идентификатор задачи
     *               * @param options - содержит:
     *               count - Количество возвращаемых записей.
     *               from - Указывается идентификатор записи, с которой следует начать отсчет (запись не включается).
     * @return List<Video> - Все изображения из всех сообщений
     */
    @GET("tasks/{task-id}/conversation/videos")
    Call<BaseResponseModel<List<Video>>> getAllVideosFromAllConversations(@Path("task-id") long taskId,
                                                                          @QueryMap Map<String, Object> options);

    /**
     * Получение всех документов из всех сообщений
     *
     * @param taskId - Идентификатор задачи
     *               * @param options - содержит:
     *               count - Количество возвращаемых записей.
     *               from - Указывается идентификатор записи, с которой следует начать отсчет (запись не включается).
     * @return List<Document> - Все изображения из всех сообщений
     */
    @GET("tasks/{task-id}/conversation/documents")
    Call<BaseResponseModel<List<Document>>> getAllDocumentsFromAllConversations(@Path("task-id") long taskId,
                                                                                @QueryMap Map<String, Object> options);

    /**
     * Получение информации по диалогу
     *
     * @param taskId - Идентификатор задачи
     * @return ConversationInfo - Инфа по диалогу
     */
    @GET("tasks/{task-id}/conversation")
    Call<BaseResponseModel<ConversationInfo>> getConversationDialogInfo(@Path("task-id") long taskId);


    //                              Connection

    @GET("hello/websocket")
    Call<BaseResponseModel<String>> getConnectionAddress();

    //                           ПРОФИЛЬ

    /**
     * Получение профиля
     *
     * @param userId - user's id
     * @return Resume
     */
    @GET("users/{user-id}/resume")
    Call<BaseResponseModel<Resume>> getUserProfile(@Path("user-id") long userId);

    /**
     * Обновление профиля
     *
     * @return Resume
     */
    @PUT("my/resume")
    Call<BaseResponseModel<Resume>> updateUserProfile(@Body Resume resume);

    /**
     * Поиск страны
     *
     * @param options - сожержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     *                find - Если значения поля задано, выполняется поиск стран, название которых попадают под ключевое слово.
     * @return
     */
    @GET("countries")
    Call<BaseResponseModel<List<CountryCity>>> findCountries(@QueryMap Map<String, Object> options);

    /**
     * Поиск страны
     *
     * @param options - сожержит:
     *                count - Количество возвращаемых записей
     *                from - Указывается идентификатор записи, с которой следует начать отсчет
     *                find - Если значения поля задано, выполняется поиск городов, название которых попадают под ключевое слово.
     * @return
     */
    @GET("countries/{country-id}/cities")
    Call<BaseResponseModel<List<CountryCity>>> findCities(@Path("country-id") long countryId,
                                                          @QueryMap Map<String, Object> options);
}
