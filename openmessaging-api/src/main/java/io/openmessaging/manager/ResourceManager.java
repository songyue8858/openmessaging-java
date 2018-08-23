/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.openmessaging.manager;

import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.common.Error;
import io.openmessaging.common.Response;
import java.util.List;

/**
 * The {@code ResourceManager} is to provide a unified interface of resource management, allowing developers to manage
 * the namespace, queue and routing resources.
 * <p>
 * Create, set, get and delete are the four basic operations of {@code ResourceManager}.
 * <p>
 * {@code ResourceManager} also supports dynamic fetch and update of resource attributes.
 * <p>
 * {@link MessagingAccessPoint#resourceManager()} ()} is the unique method to obtain a {@code ResourceManager} instance.
 * Changes made through this instance will immediately apply to the message-oriented middleware (MOM) behind {@code
 * MessagingAccessPoint}.
 *
 * @version OMS 1.0.0
 * @since OMS 1.0.0
 */
public interface ResourceManager {
    /**
     * Creates a {@code Namespace} resource with some preset attributes.
     * <p>
     * A namespace wraps the OMS resources in an abstract concept that makes it appear to the users within the namespace
     * that they have their own isolated instance of the global OMS resources.
     *
     * @param nsName the name of the new namespace
     */
    Response createNamespace(String nsName);

    /**
     * Deletes an existing namespace resource.
     *
     * @param nsName the namespace needs to be deleted
     * @return {@link Response#getError()} will return {@link Error#ERROR_402} if the specific namespace does not exist
     */
    Response deleteNamespace(String nsName);

    /**
     * Gets the namespace list in the current {@code MessagingAccessPoint}.
     *
     * @return the list of all namespaces
     */
    List<String> listNamespaces();

    /**
     * Creates a {@code Queue} resource in the configured namespace with some preset attributes.
     * <p>
     * The standard OMS {@code Queue} schema must start with the {@code Namespace} prefix:
     * <p>
     * {@literal <namespace_name>://<queue_name>}
     *
     * @param queueName the name of the new queue
     * @return {@link Response#getError()} will return {@link Error#ERROR_402} if the specific namespace does not exist
     */
    Response createQueue(String queueName);

    /**
     * Creates a {@code Queue} resource in the configured namespace with some preset attributes.
     * <p>
     * The standard OMS {@code Queue} schema must start with the {@code Namespace} prefix:
     * <p>
     * {@literal <namespace_name>://<queue_name>}
     *
     * @param queueName the name of the new queue
     * @param queueConfig the preset config
     * @return {@link Response#getError()} will return {@link Error#ERROR_402} if the specific namespace does not exist
     */
    Response createQueue(String queueName, QueueConfig queueConfig);

    /**
     * Sets the attributes of the specified queue, the old attributes will be replaced by the provided attributes, only
     * the provided key will be updated.
     *
     * @param queueName the queue name
     * @param queueConfig the new attributes
     * @return {@link Response#getError()} will return {@link Error#ERROR_402} if the specific namespace does not exist
     */
    Response setQueueConfig(String queueName, QueueConfig queueConfig);

    /**
     * Gets the attributes of the specified queue.
     *
     * @param queueName the queue name
     * @return {@link Response#getError()} will return {@link Error#ERROR_403} if the specific queue does not exist
     */
    QueueConfig getQueueConfig(String queueName);

    /**
     * Deletes an existing queue resource.
     *
     * @param queueName the queue needs to be deleted
     * @return {@link Response#getError()} will return {@link Error#ERROR_403} if the specific queue does not exist
     */
    Response deleteQueue(String queueName);

    /**
     * Gets the queue list in the specific namespace.
     *
     * @param nsName the specific namespace
     * @return the list of all queues, {@link Response#getError()}  will return {@link Error#ERROR_402} if the specific
     * namespace does not exist
     */
    ListQueueResult listQueues(String nsName);

    /**
     * Duplicate current queue to target queue, to After the current queue receives the message, it will be copied to
     * the target queue.
     *
     * @param sourceQueueName original queue, user can send message to this queue
     * @param targetQueueName target queue, only receives message from original queue
     * @return
     */
    Response duplicate(String sourceQueueName, String targetQueueName);

    /**
     * Add filter to
     *
     * @param queueName
     * @param consumerId
     * @param filterString
     * @return
     */
    Response filter(String queueName, String consumerId, String filterString);

    /**
     * Deduplicate current  queue from sourceQueue, after this operation, destinationQueue  will no longer receive
     * messages sent to the source queue.
     *
     * @param sourceQueue source queue, process messages received from producer and duplicate those to target queue
     * @param targetQueue receive messages from source queue.
     * @return
     */
    Response deDuplicate(String sourceQueue, String targetQueue);

    /**
     * Gets the stream list behind the specified queue.
     *
     * @param queueName the queue name
     * @return {@link Response#getError()} will return {@link Error#ERROR_403} if the specific namespace does not exist
     */
    ListStreamResult listStreams(String queueName);

}