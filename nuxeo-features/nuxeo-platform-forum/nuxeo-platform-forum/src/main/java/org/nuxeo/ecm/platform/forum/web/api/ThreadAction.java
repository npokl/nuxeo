/*
 * (C) Copyright 2006-2007 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     ${user}
 *
 * $Id
 */
package org.nuxeo.ecm.platform.forum.web.api;

import java.io.Serializable;
import java.util.List;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.platform.comment.web.ThreadEntry;

/**
 * This Action Listener represents a Thread inside a forum.
 *
 * @author <a href="bchaffangeon@nuxeo.com">Brice Chaffangeon</a>
 */
public interface ThreadAction extends Serializable {

    /**
     * Gets the title of the Thread to be created.
     */
    String getTitle();

    /**
     * Sets the title of the Thread.
     */
    void setTitle(String title);

    /**
     * Gets the description of the Thread.
     */
    String getDescription();

    /**
     * Sets the description of the Thread.
     */
    void setDescription(String description);

    /**
     * Adds the thread inside the forum.
     */
    String addThread();

    /**
     * Returns true if the thread is moderated, false otherwise. Just used at creation time.
     */
    boolean isModerated();

    /**
     * Return the label of the moderation state of the thread
     *
     * @param thread is the thread we want
     */
    String getModerationAsString(DocumentModel thread);

    /**
     * Sets the moderation on a thread.
     */
    void setModerated(boolean moderated);

    /**
     * Get all moderators on the thread.
     */
    List<String> getModerators();

    /**
     * Returns true if the principal (logged user) is a moderator, else otherwise.
     */
    boolean isPrincipalModerator();

    /**
     * Returns true if the principal(s group is a moderator group
     */
    boolean isPrincipalGroupModerator();

    /**
     * Returns true if the thread is moderated, false otherwise. Intends to be used by a Post.
     */
    boolean isCurrentThreadModerated();

    /**
     * Returns true if the given thread is moderated, false otherwise.
     *
     * @param thread is the thread to test
     */
    boolean isThreadModerated(DocumentModel thread);

    /**
     * Gets the latest post published in given thread.
     */
    DocumentModel getLastPostPublished(DocumentModel thread);

    /**
     * Gets all available posts in the thread according the Post state and principal rights. I.e., Post that are not
     * published won't be visible for non-moderators.
     *
     * @return a list of ThreadEntry, directly usable for display with indentation
     */
    List<ThreadEntry> getPostsAsThread();

    /**
     * Gets all Posts in the Thread with the specified state. Return all posts if state is null.
     */
    List<DocumentModel> getAllPosts(DocumentModel thread, String state);

    /**
     * Gets published posts in a thread.
     */
    List<DocumentModel> getPostsPublished(DocumentModel thread);

    /**
     * Gets pending posts in a thread.
     */
    List<DocumentModel> getPostsPending(DocumentModel thread);

    /**
     * Return the parent post of the specified index of the post in the getPostsAsThread() list.
     */
    DocumentModel getParentPost(int post);

    /**
     * Return true if the parent post identified by it's number in the getPostsAsThread list is published.
     */
    boolean isParentPostPublished(int post);

    ThreadAdapter getAdapter(DocumentModel thread);

}
