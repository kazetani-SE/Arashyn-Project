## arashyn-api@1.0.0

This generator creates TypeScript/JavaScript client that utilizes [axios](https://github.com/axios/axios). The generated Node module can be used in the following environments:

Environment
* Node.js
* Webpack
* Browserify

Language level
* ES5 - you must have a Promises/A+ library installed
* ES6

Module system
* CommonJS
* ES6 module system

It can be used in both TypeScript and JavaScript. In TypeScript, the definition will be automatically resolved via `package.json`. ([Reference](https://www.typescriptlang.org/docs/handbook/declaration-files/consumption.html))

### Building

To build and compile the typescript sources to javascript use:
```
npm install
npm run build
```

### Publishing

First build the package then run `npm publish`

### Consuming

navigate to the folder of your consuming project and run one of the following commands.

_published:_

```
npm install arashyn-api@1.0.0 --save
```

_unPublished (not recommended):_

```
npm install PATH_TO_GENERATED_PACKAGE --save
```

### Documentation for API Endpoints

All URIs are relative to *http://localhost:8080*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AdminPingControllerApi* | [**ping1**](docs/AdminPingControllerApi.md#ping1) | **GET** /admin/ping | 
*ArrangeControllerApi* | [**create9**](docs/ArrangeControllerApi.md#create9) | **POST** /learning/arrange | 
*ArrangeControllerApi* | [**submit1**](docs/ArrangeControllerApi.md#submit1) | **POST** /learning/arrange/submit | 
*AuthControllerApi* | [**completeRegister**](docs/AuthControllerApi.md#completeregister) | **POST** /auth/complete-register | 
*AuthControllerApi* | [**initiateRegistration**](docs/AuthControllerApi.md#initiateregistration) | **POST** /auth/verify | 
*AuthControllerApi* | [**login**](docs/AuthControllerApi.md#login) | **POST** /auth/login | 
*AuthControllerApi* | [**logout**](docs/AuthControllerApi.md#logout) | **POST** /auth/logout | 
*AuthControllerApi* | [**refresh**](docs/AuthControllerApi.md#refresh) | **POST** /auth/refresh | 
*AuthControllerApi* | [**register**](docs/AuthControllerApi.md#register) | **POST** /auth/register | 
*AuthControllerApi* | [**resendVerification**](docs/AuthControllerApi.md#resendverification) | **POST** /auth/verify/resend | 
*AuthControllerApi* | [**verifyRegistration**](docs/AuthControllerApi.md#verifyregistration) | **POST** /auth/verify/confirm | 
*DeckProtectedControllerApi* | [**assignDeck**](docs/DeckProtectedControllerApi.md#assigndeck) | **POST** /protected/deck/assign | 
*DeckProtectedControllerApi* | [**checkDeckUpdate**](docs/DeckProtectedControllerApi.md#checkdeckupdate) | **GET** /protected/deck/check-update/{user_deck_id} | 
*DeckProtectedControllerApi* | [**createDeck**](docs/DeckProtectedControllerApi.md#createdeck) | **POST** /protected/deck/create | 
*DeckProtectedControllerApi* | [**deleteDeck**](docs/DeckProtectedControllerApi.md#deletedeck) | **DELETE** /protected/deck/{deck_id} | 
*DeckProtectedControllerApi* | [**updateDeck**](docs/DeckProtectedControllerApi.md#updatedeck) | **POST** /protected/deck/update | 
*DeckPublicControllerApi* | [**getDeck**](docs/DeckPublicControllerApi.md#getdeck) | **GET** /public/deck/{deck_id} | 
*DeckPublicControllerApi* | [**getDeckList**](docs/DeckPublicControllerApi.md#getdecklist) | **GET** /public/deck | 
*EmailControllerApi* | [**sendVerificationEmail**](docs/EmailControllerApi.md#sendverificationemail) | **POST** /email/send-mail | 
*ExampleControllerApi* | [**create7**](docs/ExampleControllerApi.md#create7) | **POST** /meanings/{meaningId}/examples | 
*ExportControllerApi* | [**exportCsvTemplate**](docs/ExportControllerApi.md#exportcsvtemplate) | **POST** /export/csv | 
*ExportControllerApi* | [**exportTextTemplate**](docs/ExportControllerApi.md#exporttexttemplate) | **POST** /export/text | 
*FillBlankControllerApi* | [**create8**](docs/FillBlankControllerApi.md#create8) | **POST** /learning/fill_blank | 
*FillBlankControllerApi* | [**submit**](docs/FillBlankControllerApi.md#submit) | **POST** /learning/fill_blank/submit | 
*FolderProtectedControllerApi* | [**checkUpdate**](docs/FolderProtectedControllerApi.md#checkupdate) | **GET** /protected/folder/check-update/{user_folder_id} | 
*FolderProtectedControllerApi* | [**create3**](docs/FolderProtectedControllerApi.md#create3) | **POST** /protected/folder | 
*FolderProtectedControllerApi* | [**delete3**](docs/FolderProtectedControllerApi.md#delete3) | **DELETE** /protected/folder/{id} | 
*FolderProtectedControllerApi* | [**update3**](docs/FolderProtectedControllerApi.md#update3) | **PUT** /protected/folder | 
*FolderPublicControllerApi* | [**getFolder**](docs/FolderPublicControllerApi.md#getfolder) | **GET** /public/folder/{id} | 
*FolderPublicControllerApi* | [**getFolderList**](docs/FolderPublicControllerApi.md#getfolderlist) | **GET** /public/folder | 
*FormProtectedControllerApi* | [**create6**](docs/FormProtectedControllerApi.md#create6) | **POST** /protected/forms | 
*FormPublicControllerApi* | [**findByLanguage**](docs/FormPublicControllerApi.md#findbylanguage) | **GET** /public/forms | 
*GrammarProtectedControllerApi* | [**assignFilters**](docs/GrammarProtectedControllerApi.md#assignfilters) | **POST** /protected/grammar/{grammarId}/filters | 
*GrammarProtectedControllerApi* | [**create5**](docs/GrammarProtectedControllerApi.md#create5) | **POST** /protected/grammar/create | 
*GrammarProtectedControllerApi* | [**createMultiple**](docs/GrammarProtectedControllerApi.md#createmultiple) | **POST** /protected/grammar/create_multiple | 
*GrammarProtectedControllerApi* | [**deleteGrammar**](docs/GrammarProtectedControllerApi.md#deletegrammar) | **DELETE** /protected/grammar/{grammarId} | 
*GrammarProtectedControllerApi* | [**extendGrammar**](docs/GrammarProtectedControllerApi.md#extendgrammar) | **POST** /protected/grammar/{grammarId}/extend | 
*GrammarProtectedControllerApi* | [**getUpdateDetail**](docs/GrammarProtectedControllerApi.md#getupdatedetail) | **GET** /protected/grammar/edit/{grammarId} | 
*GrammarProtectedControllerApi* | [**restoreGrammar**](docs/GrammarProtectedControllerApi.md#restoregrammar) | **POST** /protected/grammar/{grammarId}/restore | 
*GrammarProtectedControllerApi* | [**update4**](docs/GrammarProtectedControllerApi.md#update4) | **POST** /protected/grammar/update | 
*GrammarPublicControllerApi* | [**checkGrammarExist**](docs/GrammarPublicControllerApi.md#checkgrammarexist) | **POST** /public/grammar/check-exist | 
*GrammarPublicControllerApi* | [**checkSimilarGrammar**](docs/GrammarPublicControllerApi.md#checksimilargrammar) | **POST** /public/grammar/similar | 
*GrammarPublicControllerApi* | [**getDetail**](docs/GrammarPublicControllerApi.md#getdetail) | **GET** /public/grammar/{grammarId} | 
*GrammarPublicControllerApi* | [**getItems**](docs/GrammarPublicControllerApi.md#getitems) | **GET** /public/grammar/item_list/grammar | 
*GrammarPublicControllerApi* | [**getPublicGrammars**](docs/GrammarPublicControllerApi.md#getpublicgrammars) | **POST** /public/grammar | 
*GrammarPublicControllerApi* | [**search**](docs/GrammarPublicControllerApi.md#search) | **GET** /public/grammar/search | 
*ImportControllerApi* | [**exportTemplate**](docs/ImportControllerApi.md#exporttemplate) | **POST** /import/cvs | 
*MeaningControllerApi* | [**create11**](docs/MeaningControllerApi.md#create11) | **POST** /grammar/{grammarId}/meanings | 
*NoteControllerApi* | [**create10**](docs/NoteControllerApi.md#create10) | **POST** /grammar/{grammarId}/notes | 
*PingControllerApi* | [**me**](docs/PingControllerApi.md#me) | **GET** /ping/me | 
*PingControllerApi* | [**ping**](docs/PingControllerApi.md#ping) | **GET** /ping | 
*ProficiencyControllerApi* | [**update5**](docs/ProficiencyControllerApi.md#update5) | **PATCH** /learning/proficiency | 
*SystemFilterProtectedControllerApi* | [**create4**](docs/SystemFilterProtectedControllerApi.md#create4) | **POST** /protected/system-filters | 
*SystemFilterPublicControllerApi* | [**getAllSystemFilters**](docs/SystemFilterPublicControllerApi.md#getallsystemfilters) | **GET** /public/system-filters | 
*UserDeckControllerApi* | [**checkDuplicate2**](docs/UserDeckControllerApi.md#checkduplicate2) | **POST** /user_deck/check_duplicate | 
*UserDeckControllerApi* | [**clone1**](docs/UserDeckControllerApi.md#clone1) | **POST** /user_deck/clone | 
*UserDeckControllerApi* | [**create2**](docs/UserDeckControllerApi.md#create2) | **POST** /user_deck | 
*UserDeckControllerApi* | [**delete2**](docs/UserDeckControllerApi.md#delete2) | **DELETE** /user_deck/{id} | 
*UserDeckControllerApi* | [**detail2**](docs/UserDeckControllerApi.md#detail2) | **GET** /user_deck/{id} | 
*UserDeckControllerApi* | [**list2**](docs/UserDeckControllerApi.md#list2) | **GET** /user_deck | 
*UserDeckControllerApi* | [**update2**](docs/UserDeckControllerApi.md#update2) | **PUT** /user_deck | 
*UserFolderControllerApi* | [**checkDuplicate1**](docs/UserFolderControllerApi.md#checkduplicate1) | **POST** /user_folder/check_duplicate | 
*UserFolderControllerApi* | [**clone**](docs/UserFolderControllerApi.md#clone) | **POST** /user_folder/clone | 
*UserFolderControllerApi* | [**create1**](docs/UserFolderControllerApi.md#create1) | **POST** /user_folder | 
*UserFolderControllerApi* | [**delete1**](docs/UserFolderControllerApi.md#delete1) | **DELETE** /user_folder/{id} | 
*UserFolderControllerApi* | [**detail1**](docs/UserFolderControllerApi.md#detail1) | **GET** /user_folder/{id} | 
*UserFolderControllerApi* | [**list1**](docs/UserFolderControllerApi.md#list1) | **GET** /user_folder | 
*UserFolderControllerApi* | [**root**](docs/UserFolderControllerApi.md#root) | **GET** /user_folder/root | 
*UserFolderControllerApi* | [**update1**](docs/UserFolderControllerApi.md#update1) | **PUT** /user_folder | 
*UserGrammarControllerApi* | [**_delete**](docs/UserGrammarControllerApi.md#_delete) | **DELETE** /user_grammar/{id} | 
*UserGrammarControllerApi* | [**checkDuplicate**](docs/UserGrammarControllerApi.md#checkduplicate) | **POST** /user_grammar/check_duplicate | 
*UserGrammarControllerApi* | [**checkDuplicateMultiple**](docs/UserGrammarControllerApi.md#checkduplicatemultiple) | **POST** /user_grammar/check_duplicate_multiple | 
*UserGrammarControllerApi* | [**create**](docs/UserGrammarControllerApi.md#create) | **POST** /user_grammar | 
*UserGrammarControllerApi* | [**detail**](docs/UserGrammarControllerApi.md#detail) | **GET** /user_grammar/{id} | 
*UserGrammarControllerApi* | [**list**](docs/UserGrammarControllerApi.md#list) | **GET** /user_grammar | 
*UserGrammarControllerApi* | [**update**](docs/UserGrammarControllerApi.md#update) | **PUT** /user_grammar | 


### Documentation For Models

 - [AnswerResult](docs/AnswerResult.md)
 - [AssignFilterRequest](docs/AssignFilterRequest.md)
 - [Children](docs/Children.md)
 - [CompleteRegisterRequest](docs/CompleteRegisterRequest.md)
 - [ComponentCreateRequest](docs/ComponentCreateRequest.md)
 - [CreateArrangeRequest](docs/CreateArrangeRequest.md)
 - [CreateArrangeResponse](docs/CreateArrangeResponse.md)
 - [CreateFillBlankResponse](docs/CreateFillBlankResponse.md)
 - [CreateFillBlankTestRequest](docs/CreateFillBlankTestRequest.md)
 - [DeckAssignGrammarRequest](docs/DeckAssignGrammarRequest.md)
 - [DeckCheckUpdateResponse](docs/DeckCheckUpdateResponse.md)
 - [DeckCreateRequest](docs/DeckCreateRequest.md)
 - [DeckDetailResponse](docs/DeckDetailResponse.md)
 - [DeckIdResponse](docs/DeckIdResponse.md)
 - [DeckListResponse](docs/DeckListResponse.md)
 - [DeckSummariseResponse](docs/DeckSummariseResponse.md)
 - [DeckUpdateRequest](docs/DeckUpdateRequest.md)
 - [ExampleCreateRequest](docs/ExampleCreateRequest.md)
 - [ExistingGrammarResponse](docs/ExistingGrammarResponse.md)
 - [FolderCheckUpdateResponse](docs/FolderCheckUpdateResponse.md)
 - [FolderCreateRequest](docs/FolderCreateRequest.md)
 - [FolderDetailResponse](docs/FolderDetailResponse.md)
 - [FolderIdResponse](docs/FolderIdResponse.md)
 - [FolderListResponse](docs/FolderListResponse.md)
 - [FolderSummariseResponse](docs/FolderSummariseResponse.md)
 - [FolderUpdateRequest](docs/FolderUpdateRequest.md)
 - [FormCreateRequest](docs/FormCreateRequest.md)
 - [FormResponse](docs/FormResponse.md)
 - [GrammarComponentSummaryResponse](docs/GrammarComponentSummaryResponse.md)
 - [GrammarCreateMultipleRequest](docs/GrammarCreateMultipleRequest.md)
 - [GrammarCreateRequest](docs/GrammarCreateRequest.md)
 - [GrammarCreateResponse](docs/GrammarCreateResponse.md)
 - [GrammarDetailResponse](docs/GrammarDetailResponse.md)
 - [GrammarEditResponse](docs/GrammarEditResponse.md)
 - [GrammarExtendRequest](docs/GrammarExtendRequest.md)
 - [GrammarFilterResponse](docs/GrammarFilterResponse.md)
 - [GrammarListRequest](docs/GrammarListRequest.md)
 - [GrammarListResponse](docs/GrammarListResponse.md)
 - [GrammarMeaningSummaryResponse](docs/GrammarMeaningSummaryResponse.md)
 - [GrammarNoteResponse](docs/GrammarNoteResponse.md)
 - [GrammarSimilarItem](docs/GrammarSimilarItem.md)
 - [GrammarSimilarResponse](docs/GrammarSimilarResponse.md)
 - [GrammarSummaryResponse](docs/GrammarSummaryResponse.md)
 - [GrammarUpdateRequest](docs/GrammarUpdateRequest.md)
 - [Group](docs/Group.md)
 - [ListFormResponse](docs/ListFormResponse.md)
 - [ListSystemFilterResponse](docs/ListSystemFilterResponse.md)
 - [LoginRequest](docs/LoginRequest.md)
 - [LoginResponse](docs/LoginResponse.md)
 - [MeaningCreate](docs/MeaningCreate.md)
 - [MeaningCreateBase](docs/MeaningCreateBase.md)
 - [MeaningCreateRequest](docs/MeaningCreateRequest.md)
 - [NoteCreateRequest](docs/NoteCreateRequest.md)
 - [PoolItem](docs/PoolItem.md)
 - [Question](docs/Question.md)
 - [QuestionComponent](docs/QuestionComponent.md)
 - [RegisterRequest](docs/RegisterRequest.md)
 - [RegisterResponse](docs/RegisterResponse.md)
 - [RegisterVerifyRequest](docs/RegisterVerifyRequest.md)
 - [ResendVerificationRequest](docs/ResendVerificationRequest.md)
 - [SendSingleMailRequest](docs/SendSingleMailRequest.md)
 - [SubmitAnswer](docs/SubmitAnswer.md)
 - [SubmitArrangeRequest](docs/SubmitArrangeRequest.md)
 - [SubmitArrangeResponse](docs/SubmitArrangeResponse.md)
 - [SubmitFillBlankRequest](docs/SubmitFillBlankRequest.md)
 - [SubmitFillBlankResponse](docs/SubmitFillBlankResponse.md)
 - [SystemFilterCreateRequest](docs/SystemFilterCreateRequest.md)
 - [SystemFilterResponse](docs/SystemFilterResponse.md)
 - [TemplateExportRequest](docs/TemplateExportRequest.md)
 - [UpdateProficiencyRequest](docs/UpdateProficiencyRequest.md)
 - [UserDeckCloneRequest](docs/UserDeckCloneRequest.md)
 - [UserDeckCreateRequest](docs/UserDeckCreateRequest.md)
 - [UserDeckDetailResponse](docs/UserDeckDetailResponse.md)
 - [UserDeckDuplicateCheckRequest](docs/UserDeckDuplicateCheckRequest.md)
 - [UserDeckDuplicateCheckResponse](docs/UserDeckDuplicateCheckResponse.md)
 - [UserDeckIdResponse](docs/UserDeckIdResponse.md)
 - [UserDeckListResponse](docs/UserDeckListResponse.md)
 - [UserDeckSummariseResponse](docs/UserDeckSummariseResponse.md)
 - [UserDeckUpdateRequest](docs/UserDeckUpdateRequest.md)
 - [UserFolderCloneRequest](docs/UserFolderCloneRequest.md)
 - [UserFolderCreateRequest](docs/UserFolderCreateRequest.md)
 - [UserFolderDetailResponse](docs/UserFolderDetailResponse.md)
 - [UserFolderDuplicateCheckRequest](docs/UserFolderDuplicateCheckRequest.md)
 - [UserFolderDuplicateCheckResponse](docs/UserFolderDuplicateCheckResponse.md)
 - [UserFolderIdResponse](docs/UserFolderIdResponse.md)
 - [UserFolderListResponse](docs/UserFolderListResponse.md)
 - [UserFolderSummariseResponse](docs/UserFolderSummariseResponse.md)
 - [UserFolderUpdateRequest](docs/UserFolderUpdateRequest.md)
 - [UserGrammarCreateMultipleRequest](docs/UserGrammarCreateMultipleRequest.md)
 - [UserGrammarCreateRequest](docs/UserGrammarCreateRequest.md)
 - [UserGrammarDetailResponse](docs/UserGrammarDetailResponse.md)
 - [UserGrammarDuplicateCheckMultipleRequest](docs/UserGrammarDuplicateCheckMultipleRequest.md)
 - [UserGrammarDuplicateCheckMultipleResponse](docs/UserGrammarDuplicateCheckMultipleResponse.md)
 - [UserGrammarDuplicateCheckRequest](docs/UserGrammarDuplicateCheckRequest.md)
 - [UserGrammarDuplicateCheckResponse](docs/UserGrammarDuplicateCheckResponse.md)
 - [UserGrammarIdResponse](docs/UserGrammarIdResponse.md)
 - [UserGrammarListResponse](docs/UserGrammarListResponse.md)
 - [UserGrammarSummarisedResponse](docs/UserGrammarSummarisedResponse.md)
 - [UserGrammarUpdateRequest](docs/UserGrammarUpdateRequest.md)
 - [VerifyOtpRequest](docs/VerifyOtpRequest.md)


<a id="documentation-for-authorization"></a>
## Documentation For Authorization


Authentication schemes defined for the API:
<a id="BearerAuth"></a>
### BearerAuth

- **Type**: Bearer authentication (JWT)

