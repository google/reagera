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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests for {@link Store}.
 */
@RunWith(JUnit4.class)
public final class StoreTest {
  private static final Reducer<Integer, Action> REDUCER =
      (state, action) -> action.equals(Action.INCREMENT) ? ++state : state;

  private enum Action {
    INCREMENT
  }

  private Store<Integer, Action> store;

  @Test
  public void dispatch() {
    store = new Store<>(0, REDUCER);
    assertThat(store.getState()).isEqualTo(0);
    store.dispatch(Action.INCREMENT);
    assertThat(store.getState()).isEqualTo(1);
  }

  @Test
  public void subscribe() {
    store = new Store<>(0, REDUCER);
    final boolean[] wasNotified = new boolean[1];
    Listener<Integer> listener = () -> {
      wasNotified[0] = true;
    };
    store.subscribe(listener);
    store.dispatch(Action.INCREMENT);
    assertThat(wasNotified[0]).isTrue();
  }

  @Test
  public void unsubscribe() {
    store = new Store<>(0, REDUCER);
    Listener<Integer> listener = () -> {
      fail("was unsubscribed but got notified");
    };
    store.subscribe(listener).unsubscribe();
    store.dispatch(Action.INCREMENT);
  }
}
