/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CommentComponentsPage, CommentDeleteDialog, CommentUpdatePage } from './comment.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Comment e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let commentUpdatePage: CommentUpdatePage;
    let commentComponentsPage: CommentComponentsPage;
    let commentDeleteDialog: CommentDeleteDialog;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Comments', async () => {
        await navBarPage.goToEntity('comment');
        commentComponentsPage = new CommentComponentsPage();
        expect(await commentComponentsPage.getTitle()).to.eq('Comments');
    });

    it('should load create Comment page', async () => {
        await commentComponentsPage.clickOnCreateButton();
        commentUpdatePage = new CommentUpdatePage();
        expect(await commentUpdatePage.getPageTitle()).to.eq('Create or edit a Comment');
        await commentUpdatePage.cancel();
    });

    it('should create and save Comments', async () => {
        const nbButtonsBeforeCreate = await commentComponentsPage.countDeleteButtons();

        await commentComponentsPage.clickOnCreateButton();
        await commentUpdatePage.setLabelInput('label');
        expect(await commentUpdatePage.getLabelInput()).to.eq('label');
        await commentUpdatePage.setCommentInput('comment');
        expect(await commentUpdatePage.getCommentInput()).to.eq('comment');
        await commentUpdatePage.setPositionxInput('5');
        expect(await commentUpdatePage.getPositionxInput()).to.eq('5');
        await commentUpdatePage.setPositionyInput('5');
        expect(await commentUpdatePage.getPositionyInput()).to.eq('5');
        await commentUpdatePage.setWidthInput('5');
        expect(await commentUpdatePage.getWidthInput()).to.eq('5');
        await commentUpdatePage.setHeigthInput('5');
        expect(await commentUpdatePage.getHeigthInput()).to.eq('5');
        await commentUpdatePage.setModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await commentUpdatePage.getModifiedInput()).to.contain('2001-01-01T02:30');
        await commentUpdatePage.setEcheanceInput('2000-12-31');
        expect(await commentUpdatePage.getEcheanceInput()).to.eq('2000-12-31');
        await commentUpdatePage.setImageInput(absolutePath);
        await commentUpdatePage.typeSelectLastOption();
        await commentUpdatePage.stateSelectLastOption();
        await commentUpdatePage.iconSelectLastOption();
        await commentUpdatePage.visiteSelectLastOption();
        await commentUpdatePage.documentsSelectLastOption();
        await commentUpdatePage.save();
        expect(await commentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await commentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Comment', async () => {
        const nbButtonsBeforeDelete = await commentComponentsPage.countDeleteButtons();
        await commentComponentsPage.clickOnLastDeleteButton();

        commentDeleteDialog = new CommentDeleteDialog();
        expect(await commentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Comment?');
        await commentDeleteDialog.clickOnConfirmButton();

        expect(await commentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
