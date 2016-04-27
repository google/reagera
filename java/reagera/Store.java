/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package reagera;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The main keeper of all state. An app should have at most one instance
 * of this class. {@link S} denotes the type of the state and {@link A} denotes
 * the type of the actions. Both {@link S} and {@link A} should be immutable.
 *
 * <p>Note: This class is thread safe.<p/>
 */
public final class Store<S, A> {
  private AtomicReference<S> stateRef;
  private final Reducer<S, A> rootReducer;
  private final Set<Listener> listeners;

  public Store(S initialState, Reducer<S, A> rootReducer) {
    this.stateRef = new AtomicReference<>(initialState);
    this.rootReducer = rootReducer;
    this.listeners = Collections.newSetFromMap(new ConcurrentHashMap<>());
  }

  /** Dispatches an action to the {@link Store} which may change the state. */
  public void dispatch(A action) {
    S state;
    do {
      state = stateRef.get();
    } while (!stateRef.compareAndSet(state, rootReducer.reduce(state, action)));
    listeners.forEach(Listener::stateChanged);
  }

  /** Retrieves the current state. */
  public S getState() {
    return stateRef.get();
  }

  /** Subscribes the {@link Listener} to state changes. */
  public Subscription subscribe(Listener<S> listener) {
    listeners.add(listener);
    return () -> listeners.remove(listener);
  }
}
